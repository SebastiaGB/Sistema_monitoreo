package Iotib.decoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MilesightPayload {

    // Arreglo para almacenar el payload como caracteres
    private final char[] c_payload;
    private final char[] type = new char[2]; 

    // URL de la base de datos MySQL y variables relacionadas
    static String URL = "jdbc:mysql://localhost:3306/iotib2";
    static database iotib = new database(URL, "root", "root");
    static Connection connection;
    static PreparedStatement preparedStatement;
    private int posicion;
    
    // Variables para almacenar diferentes datos procesados
    private int Bat = 0;
    private String DATA1 = null;
    private String DATA2 = null;
    private String DATA3 = null;
    private String DATA4 = null;
    private String DATA5 = null;
    private int DATA6 = 0;
    private double DATA7 = 0;
    private double DATA8 = 0;
    private int DATA9 = 0;
    private int DATA10 = 0;
    private int DATA11 = 0;
    private int DATA12 = 0;
    private double DATA13 = 0;
    private int DATA14 = 0;
    private int DATA15 = 0;
    //datas añadidas para medir la presencia en grafana
    private int DATAX = 0;
    private String DATAY =null;
    private final String deveui;

    //////////////////////////////////////////////////////////////
    private String Version(char[] pay) {
        // Eliminar el dígito '0' a la izquierda
        int startIndex = (pay[0] == '0') ? 1 : 0;
        char[] trimmedArray = new char[pay.length - startIndex];
        System.arraycopy(pay, startIndex, trimmedArray, 0, trimmedArray.length);

        // Construir el string con puntos entre los dígitos
        StringBuilder version = new StringBuilder();
        boolean puntoAgregado = false;

        for (int i = 0; i < trimmedArray.length; ++i) {
            version.append(trimmedArray[i]);

            // Agregar punto solo si no se ha agregado antes y el carácter actual no es un punto
            if (!puntoAgregado && trimmedArray[i] != '.') {
                version.append('.');
                puntoAgregado = true;
            }
        }

        return version.toString();
    }

    //Metodo para passar de un array de x caracteres a binario y de binario a decimal 
    private int Conversor(int p, int longitud) {
        StringBuilder sb = new StringBuilder();
        char[] newChar = new char[longitud];

        if (longitud / 4 == 4) {
            for (int j = 0; j < longitud; j++) {
                newChar[j] = (Binario(p + 2) + Binario(p + 3) + Binario(p) + Binario(p + 1)).charAt(j);
                sb.append(newChar[j]);
            }
        } else if (longitud / 4 == 2) {
            for (int j = 0; j < longitud; j++) {
                newChar[j] = (Binario(p) + Binario(p + 1)).charAt(j);
                sb.append(newChar[j]);
            }

        }

        String valor = sb.toString();
        int decimal = Integer.parseInt(valor, 2);

        return decimal;
    }//FIN//FIN

    //Convierte x caracteres de c_payload en decimal 
    private int Hexadecimal(int x, int y) {

        String hexadecimal = new String(c_payload, x, y);   //especifica la porción de caracteres que se convertirán a hexadecimal
        int decimal = Integer.parseInt(hexadecimal, 16);    //convierte en entero un hexadecimal 

        return decimal;
    }//FIN

    //Convierte un caracter de c_payload en binario
    private String Binario(int bitnum) {

        if (c_payload[bitnum] >= 48 && c_payload[bitnum] <= 57) {
            String binario = Integer.toBinaryString(c_payload[bitnum] - 48); //Convertir el valor decimal a binario como una cadena

            while (binario.length() < 4) {
                binario = "0" + binario;
            }
            return binario;

        } else {

            String binario = Integer.toBinaryString(Hexadecimal(bitnum, 1));

            while (binario.length() < 4) {
                binario = "0" + binario;
            }
            return binario;
        }

    }//FIN

    //switch de classes A,B y C
    private void Classes(int longitud) {

        switch (c_payload[longitud]) {
            case '0' -> {
                switch (c_payload[longitud + 1]) {
                    case '0' -> {
                        System.out.print(" Class A");
                        char letra = 'A';
                        DATA6 = (int) letra;
                    }
                    case '1' -> {
                        System.out.print(" Class B");
                        char letra = 'B';
                        DATA6 = (int) letra;
                    }
                    case '2' -> {
                        System.out.print(" Class C");
                        char letra = 'C';
                        DATA6 = (int) letra;
                    }
                }
            }
        }
    }//FIN

    //Convierte el segundoByte en binario y te hace la función especifica siguiente
    private void SegundoByte(int longitud) {

        String StringSegundoByte = Binario(longitud + 2) + Binario(longitud + 3);   //crear una string que una ambas string de digitos binarios
        char[] ArraySegundoByte = StringSegundoByte.toCharArray();    //Convertirla en cadena
        String[] value = {"", "Presure", "TV0C", "C02", "Ligth", "Activity", "Humity", "Temperature"};  //Crear una cadena de Strings

        for (int j = 1; j < ArraySegundoByte.length; j++) {
            if (ArraySegundoByte[j] == '1') {
                System.out.print("\n     " + ArraySegundoByte[j] + " => " + value[j] + " -> On");
            } else {
                System.out.print("\n     " + ArraySegundoByte[j] + " => " + value[j] + " -> Off");
            }
        }
        System.out.println("");//visualització per pantalla millora
    }//FIN

    //Subdatas
    private void Subdatas(int longitud) {

        String StringSegundoByte = Binario(longitud + 2) + Binario(longitud + 3);   //crear una string que una ambas string de digitos binarios
        char[] ArraySegundoByte = StringSegundoByte.toCharArray();    //Convertirla en cadena
        String[] value = {"", "Presure", "TV0C", "C02", "Ligth", "Activity", "Humity", "Temperature"};  //Crear una cadena de Strings

        for (int j = 1; j < ArraySegundoByte.length; j++) {
            if (ArraySegundoByte[j] == '1') {

                switch (value[j]) {
                    case "Presure":
                        DATA13 = 1; //"On";
                        break;
                    case "TV0C":
                        DATA12 = 1; //"On";
                        break;
                    case "C02":
                        DATA11 = 1; //"On";
                        break;
                    case "Ligth":
                        DATA10 = 1; //"On";
                        break;
                    case "Activity":
                        DATA9 = 1; //"On";
                        break;
                    case "Humity":
                        DATA8 = 1; //"On";
                        break;
                    case "Temperature":
                        DATA7 = 1; //"On";
                        break;

                }

            } else {

                switch (value[j]) {
                    case "Presure":
                        DATA13 = 0; //"Off";
                        break;
                    case "TV0C":
                        DATA12 = 0; //"Off";
                        break;
                    case "C02":
                        DATA11 = 0; //"Off";
                        break;
                    case "Ligth":
                        DATA10 = 0; //"Off";
                        break;
                    case "Activity":
                        DATA9 = 0; //"Off";
                        break;
                    case "Humity":
                        DATA8 = 0; //"Off";
                        break;
                    case "Temperature":
                        DATA7 = 0; //"Off";
                        break;
                }

            }

        }

    }
    

    // Constructor que recibe un payload y una conexión a la base de datos
    public MilesightPayload(String payload, Connection connection) throws SQLException {
        c_payload = payload.toCharArray();
        this.deveui = iotib.obtenerXstring(connection, "DEV_EUI", "data");
    }

    // Método para procesar los payloads de uplink
    public void FrameCode() throws SQLException {

        connection = iotib.abrirConexion();
        String direction = iotib.obtenerXstring(connection, "Direction", "data");
        int puerto = iotib.obtenerXint(connection, "port", "data");

        //que sea uplink y no del puerto 0 que sabemos que está reservado para protocolos LoRa
        if ("up".equals(direction) && puerto != 0) {

            int[] pos = new int[c_payload.length];

            for (int i = 0; i < pos.length; i++) {
                posicion = (i + 1) % pos.length;
                pos[i] = posicion; //posicion es igual a la posición del array pos
            }
            
            for (int i = 0; i < c_payload.length - 3; i++) {    //Bucle principal para recorrer el payload introducido
                posicion = 0;
                StringBuilder stringBuilder = new StringBuilder();

                //BASIC INFORMATION
                if (c_payload[i] == 'F' && c_payload[i + 1] == 'F') {   //Primera condición

                    type[0] = c_payload[i + 2];  //usamos el array type para llenarla de los siguientes dígitos de la condición
                    type[1] = c_payload[i + 3];

                    //VERSION DEL PROTOCOLO
                    if (type[0] == '0' && type[1] == '1') { //Inicio de condición dependiendo del type
                        posicion = pos[i + 3];
                        System.out.print("\n01 (Protocol Version): V" + c_payload[posicion + 1]);
                        DATA1 = "V" + c_payload[posicion + 1];    //data1

                    }
                    //DISPOSITIVO SN
                    if ((type[0] == '0' && type[1] == '8')) {

                        posicion = pos[i + 3];     //La posicion ahora esta 3 caracteres más a la derecha , ff08x 
                        System.out.print("\n08 (Device SN): ");
                        for (int j = posicion; j <= posicion + 11; j++) {
                            stringBuilder.append(c_payload[j]);
                            System.out.print(c_payload[j]);
                        }

                        DATA2 = stringBuilder.toString();    //data14
                    } else if ((type[0] == '1' && type[1] == '6')/*otro dev*/) {

                        posicion = pos[i + 3];     //La posicion ahora esta 3 caracteres más a la derecha , ff08x 
                        System.out.print("\n08 (Device SN): ");
                        for (int j = posicion; j <= posicion + 15; j++) {
                            stringBuilder.append(c_payload[j]);
                            System.out.print(c_payload[j]);
                        }

                        DATA2 = stringBuilder.toString();    //data14
                    }

                    //VERSION DEL HARDWARE
                    if (type[0] == '0' && type[1] == '9') {

                        posicion = pos[i + 3];
                        System.out.print("\n09 (Hardware Version): ");
                        int tamanoArray = 4;
                        char[] hard = new char[tamanoArray];

                        for (int j = posicion; j < posicion + tamanoArray; j++) {
                            stringBuilder.append(c_payload[j]);
                            hard[j - posicion] = c_payload[j];
                        }
                        String HW = Version(hard);

                        System.out.print(" V " + HW);
                        DATA3 = HW;

                    }
                    //VERSION DEL SOFTWARE 
                    if (type[0] == '0' && type[1] == 'A') {

                        posicion = pos[i + 3];
                        System.out.print("\n0a (Software Version): ");
                        int tamanoArray = 4;
                        char[] soft = new char[tamanoArray];

                        for (int j = posicion; j < posicion + tamanoArray; j++) {
                            stringBuilder.append(c_payload[j]);
                            soft[j - posicion] = c_payload[j];
                        }
                        String SW = Version(soft);

                        System.out.print(" V " + SW);
                        DATA4 = SW;

                    }
                    //POWER
                    if (type[0] == '0' && type[1] == 'B') {

                        System.out.print("\n0b (Power On) ");

                        DATA5 = "On";    //data5

                    }
                    //TIPO DE DISPOSITIVO
                    if (type[0] == '0' && type[1] == 'F') {

                        posicion = pos[i + 3];
                        System.out.print("\n0f (Device Type): ");
                        for (int j = posicion; j <= posicion + 1; j++) {   //cojemos solo los 2 siguientes caracteres 
                            stringBuilder.append(c_payload[j]);
                        }
                        Classes(posicion);

                    }
                    //ESTADO DE LOS SENSORES 
                    if (type[0] == '1' && type[1] == '8') {

                        posicion = pos[i + 3];
                        System.out.print("\n18 (Sensor Status): ");
                        for (int j = posicion; j <= posicion + 3; j++) {
                            stringBuilder.append(c_payload[j]);
                            System.out.print(c_payload[j]);
                        }

                        if (c_payload[posicion] == '0' && c_payload[posicion + 1] == '0') {
                            System.out.print("\n    00 => All Sensors");

                        }
                        SegundoByte(posicion);
                        Subdatas(posicion);
                    }
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////SENSOR DATA  
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                boolean temperaturaProcesada = false;   //para que en la descodificación de algunos dispositivos no nos salga la de otros.
                
                if (c_payload[i] == '0' && c_payload[i + 1] == '1' && c_payload[i + 2] == '7' && c_payload[i + 3] == '5') {     //Inicio del swtich case del sensor data 

                    int bat;
                    String bateria;
                    posicion = pos[i + 3];
                    //NIVEL DE BATERÍA
                    System.out.print("\n75 (Battery Level): ");   //nivel de batería
                    if (c_payload[posicion] == '0') {

                        bateria = Character.toString(c_payload[posicion + 1]);
                    } else {

                        bateria = c_payload[posicion] + "" + c_payload[posicion + 1];  //son dos numeros separados
                    }

                    //es passa sempre a hexadecimal
                    bat = Integer.parseInt(bateria, 16);

                    System.out.print(bat + " %");
                    Bat = bat;

                }
                //TEMPERATURA
                if (c_payload[i] == '0' && c_payload[i + 1] == '3' && c_payload[i + 2] == '6' && c_payload[i + 3] == '7') {

                    posicion = pos[i + 3];
                    System.out.print("\n67 (Temperature): ");    //temperatura
                    for (int j = posicion; j <= posicion + 3; j++) {
                        System.out.print(c_payload[j]);
                    }
                    int temp = Conversor(posicion, 16); //convertirlo en double 
                    double temperature = (double) temp / 10;

                    DATA7 = temperature;
                    System.out.print("\n    Temp = " + temperature + " grados");
                    temperaturaProcesada = true;

                } //ESTADO DE PROXIMIDAD DE OTRO DEV
                else if (!temperaturaProcesada && (c_payload[i] == '0' && c_payload[i + 1] == '3' && c_payload[i + 2] == '0' && c_payload[i + 3] == '0')) {

                    posicion = pos[i + 3];
                    System.out.print("\n00 (PIR Status): ");
                    for (int j = posicion; j <= posicion + 1; j++) {
                        System.out.print(c_payload[j]);
                    }
                    if (c_payload[posicion + 1] == '1') {
                        System.out.print(" PIR is triggered.");
                        DATA6 = 1;
                        DATA5 = "Hay presencia"; 

                    } else {
                        System.out.print(" PIR is not triggered.");
                        DATA6 = 0;
                        DATA5 = "No hay presencia";

                    }

                }
                //HUMEDAD 
                if (c_payload[i] == '0' && c_payload[i + 1] == '4' && c_payload[i + 2] == '6' && c_payload[i + 3] == '8') {

                    posicion = pos[i + 3];
                    System.out.print("\n68 (Humidity): ");   //humedad
                    for (int j = posicion; j <= posicion + 1; j++) {
                        System.out.print(c_payload[j]);
                    }
                    int hum = Conversor(posicion, 8);
                    double humidity = (double) hum / 2;

                    DATA8 = humidity;
                    System.out.print("\n    Hum = " + humidity + " %");
                    temperaturaProcesada = true;

                } //ESTADO DE BRILLO DE OTRO DEV
                else if (!temperaturaProcesada && (c_payload[i] == '0' && c_payload[i + 1] == '4' && c_payload[i + 2] == '0' && c_payload[i + 3] == '0')) {

                    posicion = pos[i + 3];
                    System.out.print("\n00 (Light Status): ");
                    for (int j = posicion; j <= posicion + 1; j++) {
                        System.out.print(c_payload[j]);
                    }
                    if (c_payload[posicion + 1] == '1') {
                        System.out.print(" Bright.");
                        DATA10 = 1;
                    } else {
                        System.out.print(" Dark.");
                        DATA10 = 0;
                    }

                }
                // PROXIMIDAD 
                if (c_payload[i] == '0' && c_payload[i + 1] == '5' && c_payload[i + 2] == '6' && c_payload[i + 3] == 'A') {

                    posicion = pos[i + 3];
                    System.out.print("\n6a (Activity Level): ");   //Nivel de actividad
                    for (int j = posicion; j <= posicion + 3; j++) {
                        System.out.print(c_payload[j]);
                    }
                    temperaturaProcesada = true;
                    DATA9 = (Conversor(posicion, 16));
                    //para grafana
                    String valor;
                    int proximidad;
                    if(DATA9 > 1){
                        proximidad = 1;
                        valor = "Hay presencia"; 
                    }else{
                        proximidad = 0;
                        valor = "No hay presencia";
                    }
                    DATA6 = proximidad;
                    DATA5 = valor;
                    System.out.print("\n    Level = " + Conversor(posicion, 16));

                }
                //LUMINOSIDAD
                if (c_payload[i] == '0' && c_payload[i + 1] == '6' && c_payload[i + 2] == '6' && c_payload[i + 3] == '5') {

                    posicion = pos[i + 3];
                    System.out.print("\n03 (Luminosity): ");     //Luminosidad
                    for (int j = posicion; j <= posicion + 11; j++) {
                        System.out.print(c_payload[j]);
                    }
                    DATA10 = (Conversor(posicion, 16));
                    DATA14 = (Conversor(posicion + 4, 16));
                    DATA15 = (Conversor(posicion + 8, 16));
                    temperaturaProcesada = true;
                    System.out.print("\n    illumination = " + Conversor(posicion, 16) + " lux");
                    System.out.print("\n    visible + infrared = " + Conversor(posicion + 4, 16) + " ");
                    System.out.print("\n    infrared = " + Conversor(posicion + 8, 16) + " ");

                }
                //NIVEL DE C02
                if (c_payload[i] == '0' && (c_payload[i + 1] == '7' || c_payload[i + 1] == '5' /*altre dev*/) && c_payload[i + 2] == '7' && c_payload[i + 3] == 'D') {

                    posicion = pos[i + 3];
                    System.out.print("\n7d (C02 Level): ");   //Nivel de C02
                    for (int j = posicion; j <= posicion + 3; j++) {
                        System.out.print(c_payload[j]);
                    }
                    temperaturaProcesada = true;
                    DATA11 = (Conversor(posicion, 16));
                    System.out.print("\n    Level = " + Conversor(posicion, 16) + " ppm");

                }
                //NIVEL DE TVOC
                if (c_payload[i] == '0' && c_payload[i + 1] == '8' && c_payload[i + 2] == '7' && c_payload[i + 3] == 'D') {

                    posicion = pos[i + 3];
                    System.out.print("\n7d (TVOC): ");   //TVOC
                    for (int j = posicion; j <= posicion + 3; j++) {
                        System.out.print(c_payload[j]);
                    }
                    temperaturaProcesada = true;
                    DATA12 = (Conversor(posicion, 16));
                    System.out.print("\n    Level = " + Conversor(posicion, 16) + " ppB");

                }
                //NIVEL DE PRESIÓN
                if (c_payload[i] == '0' && (c_payload[i + 1] == '9' || c_payload[i + 1] == '6' /*altre dev*/) && c_payload[i + 2] == '7' && c_payload[i + 3] == '3') {

                    posicion = pos[i + 3];
                    System.out.print("\n73 (Biometric Pressure): ");   //Presión biométrica
                    for (int j = posicion; j <= posicion + 3; j++) {
                        System.out.print(c_payload[j]);
                    }
                    int pres = Conversor(posicion, 16);
                    double pressure = (double) pres / 10;
                    temperaturaProcesada = true;
                    DATA13 = pressure;
                    System.out.print("\n    Pressure = " + pressure + " hPa");

                }

            }

            //iotib.añadirID(connection, "data", "milesight_payload");
            int ID = iotib.obtenerXint(connection, "ID", "data");
            int counter = iotib.obtenerXint(connection, "counter", "data");
            String tipo_dec = new String(new char[]{c_payload[0], c_payload[1]}); //para que en database se pueda dividir el switch 
            preparedStatement = iotib.actualizarMilesight(deveui, Bat, DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, DATA14, DATA15, tipo_dec, counter, ID, connection);
            iotib.cerrarConexionConDatos(connection, preparedStatement);
        } else {

            iotib.cerrarConexionSinDatos(connection);
        }

    }

}
