
package Iotib.decoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 *
 * @author sebas
 */
public class DRAGINOPayload {

    static private char[] c_payload;
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static final String URL = "jdbc:mysql://localhost:3306/iotib2";
    private static final database iotib = new database(URL, "root", "root");
    private final String deveui;
    private String DATA1 = null;
    private int DATA2 = 0;
    private int DATA0 = 0;
    private int DATA3 = 0;
    private int DATA4 = 0;
    private String DATA5 = null;
    private String DATA6 = null;
    private String DATA7 = null;
    private String DATA8 = null;
    private double DATA9 = 0;

    public DRAGINOPayload(String payload, Connection connection) throws SQLException {
        c_payload = payload.toCharArray();
        this.deveui = iotib.obtenerXstring(connection, "DEV_EUI", "data");
    }

    //de Bytes (decimal) a binario 0000
    private String Binary(char[] bin, int bitnum) {

        if (bin[bitnum] >= 48 && bin[bitnum] <= 57) {
            String binario = Integer.toBinaryString(bin[bitnum] - 48);

            while (binario.length() < 4) {
                binario = "0" + binario;
            }
            return binario;

        } else {

            String binario = Integer.toBinaryString(Hexadecimal(bin, bitnum, 1));

            while (binario.length() < 4) {
                binario = "0" + binario;
            }
            return binario;
        }

    }

    //de hexadecimal a decimal
    private int Hexadecimal(char[] hex, int x, int y) {

        String hexadecimal = new String(hex, x, y);
        int decimal = Integer.parseInt(hexadecimal, 16);

        return decimal;
    }

    //////////////////////////////////////////////////////////////////////////////
    /////////////METODOS FPORT2
    ///////////////////////////////////////////////////////////////////////////////
    private void statusAlarm(char[] byteNumber) {

        // Posición simpre serà 0 y 1 
        String str1 = Binary(byteNumber, 0);
        String str2 = Binary(byteNumber, 1);

        // Concatenar las dos cadenas
        String combinedString = str1 + str2;

        // Imprimir la cadena combinada
        //System.out.println("Cadena combinada: " + combinedString);
        // Dividir la cadena en partes
        String part1 = combinedString.substring(0, 6);
        String part2 = combinedString.substring(6, 7);
        String part3 = combinedString.substring(7);
        DATA2 = Integer.parseInt(part2);    //alarma 

        // Imprimir las partes
        //FLAG
        System.out.println("Flag(6bits): " + part1);

        //ALARM
        if ("1".equals(part2)) {
            System.out.println("Alarm");

        } else {
            System.out.println("No Alarm");

        }

        //CONTACT
        if ("1".equals(part3)) {
            System.out.println("Contact Status: " + part3 + ": Open");
                    DATA1 = "Open";    //contact status
                    DATA0 = 1;
        } else {
            System.out.println("Contact Status: " + part3 + ": Close");
                    DATA1 = "Close";    //contact status
                    DATA0 = 0;
        }

    }

    //Contador de pulsos
    private void totalPulse(char[] byteNumber) {

        int pulseCount = Hexadecimal(byteNumber, 0, byteNumber.length);
        System.out.println("Total pulse/counting: " + pulseCount);
        DATA3 = pulseCount;

    }

    //Duración de última abertura en min
    private void openDuration(char[] byteNumber) throws SQLException {

        int Count = Hexadecimal(byteNumber, 0, byteNumber.length);
        System.out.println("Last open duration: " + Count);
        DATA4 = Count;

    }

    //TimeStamp
    private void timeStamp(char[] byteNumber) {

        int timestamp = Hexadecimal(byteNumber, 0, byteNumber.length);

        Instant instant = Instant.ofEpochSecond(timestamp);

        // Convertir el Instant a un objeto LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println("TimeStamp de Unix: " + dateTime);

        // Formatear el LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        DATA5 = formattedDateTime;

    }

    //////////////////////////////////////////////////////////////////////////////
    /////////////METODOS FPORT5
    ///////////////////////////////////////////////////////////////////////////////
    //Modelo del dispositivo 
    private void Model(char[] byteNumber) {

        // Obtener el valor numérico
        int byte1 = Character.getNumericValue(byteNumber[0]);
        int byte2 = Character.getNumericValue(byteNumber[1]);

        // Convertir el valor numérico a una cadena
        String str2 = String.format("%X", byte2);
        String str1 = String.format("%X", byte1);

        // Concatenar las dos cadenas
        String combinedString = str1 + str2;
        DATA6 = combinedString;
        if (combinedString.equals("0E")) {

            System.out.println("Sensor Model: CPL01");
        } else {

            //.....otros modelos
        }

    }

    private void Version(char[] byteNumber) {

        // Eliminar el dígito '0' a la izquierda
        int startIndex = (byteNumber[0] == '0') ? 1 : 0;
        char[] trimmedArray = new char[byteNumber.length - startIndex];
        System.arraycopy(byteNumber, startIndex, trimmedArray, 0, trimmedArray.length);

        // Construir el string con puntos entre los dígitos
        StringBuilder version = new StringBuilder();
        for (int i = 0; i < trimmedArray.length; ++i) {
            version.append(trimmedArray[i]);
            if (i < trimmedArray.length - 1) {
                version.append('.');
            }
        }
        DATA7 = version.toString();
        // Imprimir el resultado
        System.out.println("Frimware Version: " + version.toString());
    }

    //banda frecuencial
    private void frequencyBand(char[] byteNumber) {

        // Obtener el valor numérico
        int byte1 = Character.getNumericValue(byteNumber[0]);
        int byte2 = Character.getNumericValue(byteNumber[1]);

        // Convertir el valor numérico a una cadena
        String str2 = String.format("%X", byte2);
        String str1 = String.format("%X", byte1);

        // Concatenar las dos cadenas
        String combinedString = str1 + str2;
        System.out.println("Frequency Band: ");

        switch (combinedString) {
            case "01":
                System.out.println("EU868");
                DATA8 = "EU868";
                break;
            case "02":
                System.out.println("US915");
                DATA8 = "US915";
                break;
            case "03":
                System.out.println("IN865");
                DATA8 = "IN865";
                break;
            case "04":
                System.out.println("AU915");
                DATA8 = "AU915";
                break;
            case "05":
                System.out.println("KZ865");
                DATA8 = "KZ865";
                break;
            case "06":
                System.out.println("RU864");
                DATA8 = "RU864";
                break;
            case "07":
                System.out.println("AS923");
                DATA8 = "AS923";
                break;
            case "08":
                System.out.println("AS923-1");
                DATA8 = "AS923-1";
                break;
            case "09":
                System.out.println("AS923-2");
                DATA8 = "AS923-2";
                break;
            case "0a":
                System.out.println("AS923-3");
                DATA8 = "AS923-3";
                break;
            case "0b":
                System.out.println("CN470");
                DATA8 = "CN470";
                break;
            case "0c":
                System.out.println("EU433");
                DATA8 = "EU433";
                break;
            case "0d":
                System.out.println("KR920");
                DATA8 = "KR920";
                break;
            case "0e":
                System.out.println("MA869");
                DATA8 = "MA869";
                break;
            default:
                System.out.println("Opción no válida");
        }

    }

    //subbanda frecuencial
    private void subBand(char[] byteNumber) {

        // Obtener el valor numérico
        int byte2 = Character.getNumericValue(byteNumber[1]);

        if (byte2 <= 8) {
            System.out.println("Frequency SubBand: 0x0" + byte2 + " : AU915 and US915");
        } else if (byte2 == 11 || byte2 == 12) {
            System.out.println("Frequency SubBand: 0x0" + byte2 + " : CN470");
        } else {
            System.out.println("Frequency SubBand: Other Bands");
        }

    }

    private void batteryInfo(char[] byteNumber) {

        int bat = Hexadecimal(byteNumber, 0, byteNumber.length);
        System.out.println("Battery: " + bat + " mV");
        int valorMax = 3680;
        double bateria = ((double) bat / valorMax) * 100;
        
        DATA9 = bateria;
    }

    private void fPORT2() throws SQLException {

        //String fPORT2 = "01 000000 000000 655B5CF4";

        // Dividir la cadena en partes
        char[] part1 = new char[]{c_payload[0], c_payload[1]};
        char[] part2 = Arrays.copyOfRange(c_payload, 2, 8);
        char[] part3 = Arrays.copyOfRange(c_payload, 8, 14);
        char[] part4 = Arrays.copyOfRange(c_payload, 14, c_payload.length);

        statusAlarm(part1);
        totalPulse(part2);
        openDuration(part3);
        timeStamp(part4);

    }

    private void fPORT5() {

        //String fPORT5 = "0E 0100 01 FF 0E4E";

        // Dividir la cadena en partes cada parte realiza una función 
        char[] part1 = new char[]{c_payload[0], c_payload[1]};
        char[] part2 = Arrays.copyOfRange(c_payload, 2, 6);
        char[] part3 = Arrays.copyOfRange(c_payload, 6, 8);
        char[] part4 = Arrays.copyOfRange(c_payload, 8, 10);
        char[] part5 = Arrays.copyOfRange(c_payload, 10, c_payload.length);

        Model(part1);
        Version(part2);
        frequencyBand(part3);
        subBand(part4);
        batteryInfo(part5);

    }

    public void FrameCode() throws SQLException {

        connection = iotib.abrirConexion();

        String direction = iotib.obtenerXstring(connection, "Direction", "data");
        int puerto = iotib.obtenerXint(connection, "port", "data");

        if ("up".equals(direction) && puerto != 0) {

            //maybe ho podem fer per payload lenght
            if (c_payload.length == 14) {
                fPORT5();
            } else if (c_payload.length == 22) {
                fPORT2();
            }

            //iotib.añadirID(connection, "data", "dragino_payload");
            int ID = iotib.obtenerXint(connection, "ID", "data");
            int counter = iotib.obtenerXint(connection, "counter", "data");
            preparedStatement = iotib.actualizarDragino(deveui, DATA0, DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, counter, ID, "UPDATE dragino_payload SET devEui = ?, ocNumber = ?,ocStatus = ?, Alarm = ?, totalPulse = ?, lastOpen = ?, unixTime = ?, Model = ?, Version = ?, Frequency = ?, Battery = ?, Counter = ? WHERE ID = ?", connection);
            iotib.cerrarConexionConDatos(connection, preparedStatement);

        } else {
            iotib.cerrarConexionSinDatos(connection);
        }
    }

}
