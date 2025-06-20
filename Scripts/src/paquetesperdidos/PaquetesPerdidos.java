package com.mycompany.paquetesperdidos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class PaquetesPerdidos {

    public static void main(String[] args) throws SQLException {
        database iotib = new database("jdbc:mysql://localhost:3306/iotib2", "root", "root");
        Connection connection = iotib.abrirConexion();

        //LA UTILITZAM PER A OBTENIR L'HORA ACTUAL I DELS MESOS PASSATS DE FORMA MANUAL
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        //LocalDateTime fechaHoraUnMesAntes = fechaHoraActual.minusMonths(1);
       
        // Definir el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Formatear la fecha
        String fechaHora = "2023-12-31 23:59:59";
        String fechaHoraMesAntes = "2023-12-01 00:00:01";
        ////////////////////////////////////////////////////////////////////////

        //CONTAM TOTES LES DEVEUIS DISTINTES QUE HI HA DINS LA TAULA DATA
        String n_arrays_dev = "SELECT COUNT(DISTINCT dev_eui) FROM data where counter = 0 or counter = 1 and direction = \"up\" and port != '0';";
        PreparedStatement preparedStatement = connection.prepareStatement(n_arrays_dev);

        ResultSet resultSet = preparedStatement.executeQuery();

        int int_arrays_dev = 0;
        if (resultSet.next()) {
            int_arrays_dev = resultSet.getInt(1);
        }

        System.out.println(int_arrays_dev);

        //CREAM UN ARRAY I GUARDAM LA LONGITUD
        String[] dev_euis = new String[int_arrays_dev];

        //FEIM UNA CONSULTA PER OBTENIR LES DEVEUS QUE TENEN COUNTER = 0 I EN CAS DE QUE NO EN TENGIN COUNTER = 1. 
        /*PORT NO HA DE SER 0 JA QUE ES UN PORT RESERVAT I ESPECÍFIC PEL PROTOCOL LORA I DIRECTION UP PER A AGAFAR NOMÉS ELS UPLINKS*/
        String consultaDev = "SELECT dev_eui as dev_eui "
                + "FROM data d1 "
                + "WHERE (counter = 0 OR (counter = 1 AND NOT EXISTS "
                + "(SELECT 1 FROM data d2 WHERE d2.counter = 0 AND d2.direction = 'up' AND d2.port != '0' AND d2.dev_eui = d1.dev_eui))) "
                + "AND direction = 'up' AND port != '0' "
                + "GROUP BY dev_eui";

        PreparedStatement preparedStatement_1 = connection.prepareStatement(consultaDev);

        ResultSet resultSet_1 = preparedStatement_1.executeQuery();
        int i = 0;

        while (resultSet_1.next()) {
            dev_euis[i] = resultSet_1.getString("dev_eui");
            i++;
        }

        //OMPLIM L'ARRAY DE DEVEIS AMB LES OBTENIDES A LA CONSULTA
        for (int j = 0; j < int_arrays_dev; j++) {
            System.out.println(dev_euis[j]);
        }
        ////////////////////////////////////////////////////////////////////////
        
        //RECORREM L'ARRAY DE DEVEUIS I ES FA LA CONSULTA MÀGICA
        /*SELECIONA EL TRXTIME DEL VALOR DE DESPRES DEL VALOR MES GRAN DE LA COLUMNA COUNTER, ES A DIR EL 
        VALOR DE DESPRÉS DEL DARRER PAQUET DE LA SESIÓ */
        /*SI NO S'HAN PERDUT PAQUETS SERIEN ELS 0 O 1*/
        for (int k = 0; k < int_arrays_dev; k++) {
            String consultaCeros = "SELECT trx_time\n"
                    + "FROM (\n"
                    + "    SELECT\n"
                    + "        t.*,\n"
                    + "        LAG(t.counter) OVER (ORDER BY t.trx_time) AS prev_counter\n"
                    + "    FROM\n"
                    + "        data t\n"
                    + "    WHERE\n"
                    + "        t.direction = \"up\" AND t.port != '0' \n"
                    + "  AND dev_eui = '" + dev_euis[k] + "'\n"
                    + ") AS subquery\n"
                    + "WHERE\n"
                    + "    subquery.counter <= subquery.prev_counter\n"
                    + "    OR subquery.prev_counter IS NULL\n"
                    + "ORDER BY\n"
                    + "    subquery.trx_time";

            PreparedStatement preparedStatement_2 = connection.prepareStatement(consultaCeros);

            ResultSet resultSet_2 = preparedStatement_2.executeQuery();

            //EL GUARDAM A UNA LLISTA
            ArrayList<String> trx_time = new ArrayList<String>();
            while (resultSet_2.next()) { //miramos si hay valor en resultSet
                trx_time.add(resultSet_2.getString("trx_time"));
            }
            //MOSTRAM AQUEST TRXTIME
            System.out.println("Trx_time: \n");
            for (int j = 0; j < trx_time.size(); j++) {

                System.out.println(trx_time.get(j));
            }

            ///////////////////////////////////////////////////////////////////
            ArrayList<String> trx_time_anterior = new ArrayList<String>();

            //RECORREM TRX TIME I OBTENIM EL VALOR JUST D'ABANS QUE ES EL VALOR MES GRAN DE COUNTER DE LA SESIÓ ANTERIOR
            for (int j = 0; j < trx_time.size(); j++) {
                String consultaTrxAnterior = "SELECT MAX(trx_time) FROM data WHERE trx_time < \"" + trx_time.get(j) + "\" and dev_eui = '" + dev_euis[k] + 
                        "' and direction = \"up\" and port != '0'";

                PreparedStatement preparedStatement_3 = connection.prepareStatement(consultaTrxAnterior);

                ResultSet resultSet_3 = preparedStatement_3.executeQuery();

                while (resultSet_3.next()) { //miramos si hay valor en resultSet

                    trx_time_anterior.add(resultSet_3.getString("MAX(trx_time)"));

                }

            }
            String consultaTrxAnterior_2 = "SELECT MAX(trx_time) as t FROM data WHERE dev_eui = '" + dev_euis[k] + "' and direction = \"up\" and port != '0'";

            PreparedStatement preparedStatement_3_2 = connection.prepareStatement(consultaTrxAnterior_2);
            ResultSet resultSet_3_2 = preparedStatement_3_2.executeQuery();

            // Obtén el valor máximo de trx_time
            String maxTrxTime = "";
            if (resultSet_3_2.next()) {
                maxTrxTime = resultSet_3_2.getString("t");
            }
            if (!trx_time.contains(maxTrxTime)) {
                trx_time_anterior.add(resultSet_3_2.getString("t"));
            }
            //MOSTRAM TR_TIME_ANTERIOR 
            System.out.println("TRX_TIME anterior: \n");
            for (int j = 0; j < trx_time_anterior.size(); j++) {
                System.out.println(trx_time_anterior.get(j));
            }

            ////////////////////////////////////////////////////////////////////

            //OBTENIR EL NOMBRE MINIM DEL CONTADOR DE LA DEVEUI CORRESPONENT PER A SABER SI COMENÇA PER 0 O 1.
            String countmin = "SELECT MIN(counter) FROM data WHERE dev_eui = '" + dev_euis[k] + "' and direction = \"up\" and port != '0'";

            PreparedStatement preparedStatement_cmin = connection.prepareStatement(countmin);
            ResultSet resultSet_cmin = preparedStatement_cmin.executeQuery();
            int minCounter = 0;
            if (resultSet_cmin.next()) {
                //obtener el valor mínimo del contador
                minCounter = resultSet_cmin.getInt(1);
            }
            
            /////////////////////////////////////////////////////////////////////
            int sumaCounters = 0; 
            //AGAFAM TOTS ELS VALORS DE COUNTER DELS DARRERS PAQUETS DE CADA SESIÓ I ELS ANAM SUMMANT PER OBTENIR EL NOMBRE TOTAL DE PAQUETS ESPERATS, ES 
            //FA ENTRE LES DATES QUE L'USUARI VULGA
            for (int j = 0; j < trx_time_anterior.size(); j++) {
                String consultaCounters = "SELECT counter FROM data WHERE dev_eui = '" + dev_euis[k] + "' and direction = \"up\" and port != '0'";

                if (trx_time_anterior.get(j) != null) {
                    consultaCounters += " AND (trx_time = \"" + trx_time_anterior.get(j) + "\" and trx_time between \"" + fechaHoraMesAntes + "\" and \"" + fechaHora + "\")";

                    PreparedStatement preparedStatement_Counters = connection.prepareStatement(consultaCounters);

                    ResultSet resultSet_Counters = preparedStatement_Counters.executeQuery();

                    while (resultSet_Counters.next()) { // miramos si hay valor en resultSet
                        int counter = resultSet_Counters.getInt(1); 
                    
                        if(minCounter == 0){
                            counter = counter+1; //si el valor mínimo del counter es 0 se le suma 1 ya que se espera un paquete más
                        }
                        sumaCounters += counter; // Acumulamos la suma en cada iteración
                    }
                }
            }
            
            
            /////////////////////////////////////////////////////////////////////////
            int npaquetes = 0;

            //CONTAM ELS NOMBRE DE PAQUETS QUE HI HA ENTRE EL VALOR MAXIM DEL CONTADOR D'UNA SESIÓ TX_T_a I EL MINIM TX_T, ENTE LES DATES QUE L'USUARI VULGA
            for (int x = 0; x < trx_time.size(); x++) {
                int w = x + 1;  // Inicializamos w basado en el valor de x

                if (w < trx_time_anterior.size()) {
                    String n_paquetes = "SELECT count(*) FROM data WHERE (trx_time between \"" + trx_time.get(x) + "\" and \"" + trx_time_anterior.get(w) + 
                            "\" and trx_time between \"" + fechaHoraMesAntes + "\" and \"" + fechaHora + "\") and dev_eui = '" + dev_euis[k] + 
                            "' and direction = \"up\" and port != '0'";

                    PreparedStatement preparedStatement_npaquetes = connection.prepareStatement(n_paquetes);
                    ResultSet resultSet_npaquetes = preparedStatement_npaquetes.executeQuery();

                    while (resultSet_npaquetes.next()) {
                        int contadorActual = resultSet_npaquetes.getInt(1);

                        npaquetes += contadorActual;
                    }
                }
            }
            ////////////////////////////////////////////////////////////////////
            
            /*//n de paquetes totales que hay
            System.out.println(npaquetes);
            //n de paquetes totales ESPERADOS
            System.out.println(sumaCounters);*/
            
            //ES FA EL CALCUL
            double paquetes_perdidos;
            int perdidas = sumaCounters - npaquetes;
            if (perdidas != 0 && sumaCounters != 0) {
                paquetes_perdidos = ((double) perdidas / sumaCounters) * 100;
            } else {

                paquetes_perdidos = 0;
            }
            System.out.println(dev_euis[k] + " = " + paquetes_perdidos + " %");

            String add = "INSERT INTO paquetesPerdidos (dev_eui, porcentajePaquetesPerdidos, periodo) VALUES ('" + dev_euis[k] + "', '" + paquetes_perdidos + "', '" + fechaHora + " - " + fechaHoraMesAntes + "')";

            PreparedStatement preparedStatement_add = connection.prepareStatement(add);

            preparedStatement_add.executeUpdate();

        }

    }

}
