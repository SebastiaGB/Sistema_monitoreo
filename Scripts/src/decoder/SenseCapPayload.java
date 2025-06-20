package Iotib.decoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SenseCapPayload {

    static private char[] c_payload;
    char[] type = new char[4];  //creamos nueva array char
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static final String URL = "jdbc:mysql://localhost:3306/iotib2";
    private static final database iotib = new database(URL, "root", "root");
    private final String deveui;
    private int posicion;
    private int DATA1 = 0;
    private int DATA2 = 0;
    private double DATA3 = 0;
    private double DATA4 = 0;
    private String DATA5 = null;

    public SenseCapPayload(String payload, Connection connection) throws SQLException {
        c_payload = payload.toCharArray();
        this.deveui = iotib.obtenerXstring(connection, "DEV_EUI", "data");
    }

    //de hexadecimal a decimal
    private int Hexadecimal(char[] hex, int x, int y) {

        String hexadecimal = new String(hex, x, y);
        int decimal = Integer.parseInt(hexadecimal, 16);

        return decimal;
    }

    public void FrameCode() throws SQLException {

        connection = iotib.abrirConexion();

        String direction = iotib.obtenerXstring(connection, "Direction", "data");
        int puerto = iotib.obtenerXint(connection, "port", "data");


        if ("up".equals(direction) && puerto != 0) {

            //usamos posicion para poder recorrer un array dentro del array y así guardar ese trozo de bytes.
            int[] pos = new int[c_payload.length];

            for (int i = 0; i < pos.length; i++) {
                posicion = (i + 1) % pos.length;
                pos[i] = posicion;
            }
            //AL ESTAR EL PAYLOAD EN HEXADECIMAL SEMPRE ELS VALORS ES PASSEN A DECIMAL 

            //recoremos los payloads hasta su longitud - 5 debido a que posicion es pos i + 5.
            for (int i = 0; i < c_payload.length - 5; i++) {
                posicion = 0;
                //creadas para obtener una string de un fragmento del array 
                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder stringBuilder2 = new StringBuilder();

                //BATERIA
                if (c_payload[i] == '0' && c_payload[i + 1] == '0') {

                    //llenamos el array type con los 4 siguienes digitos de c_payload i y c_payload i+1
                    for (int j = 0; j < 4; ++j) {
                        type[j] = c_payload[i + 2 + j];
                    }

                    if (type[0] == '0' && type[1] == '7' && type[2] == '0' && type[3] == '0') {
                        posicion = pos[i + 5];  //recorremos los 5 primeros caracteres para colocarnos en el 6 (contamos el 0)
                        //battery value
                        for (int j = posicion; j <= posicion + 3; j++) {
                            stringBuilder.append(c_payload[j]);
                        }
                        //upload interval
                        for (int j = posicion + 4; j <= posicion + 7; j++) {
                            stringBuilder2.append(c_payload[j]);
                        }

                        //CALCULEMOS BAT
                        char[] bat = stringBuilder.toString().toCharArray();
                        // Intercambiamos las posiciones 0 y 2, y 1 y 3
                        char bat0 = bat[0];
                        bat[0] = bat[2];
                        bat[2] = bat0;

                        char bat1 = bat[1];
                        bat[1] = bat[3];
                        bat[3] = bat1;

                        int devbat = Hexadecimal(bat, 0, 4);
                        System.out.println("Battery: " + devbat + "%");
                        DATA1 = devbat;

                        //CALCULEMOS intervalo
                        char[] inter = stringBuilder2.toString().toCharArray();
                        // Intercambiamos las posiciones 0 y 2, y 1 y 3
                        char inter0 = inter[0];
                        inter[0] = inter[2];
                        inter[2] = inter0;

                        char inter1 = inter[1];
                        inter[1] = inter[3];
                        inter[3] = inter1;

                        int devinter = Hexadecimal(inter, 0, 4);
                        System.out.println("Upload Interval: " + devinter + " minutes");
                        DATA2 = devinter;

                    }
                }

                if (c_payload[i] == '0' && c_payload[i + 1] == '1') {

                    //llenamos el array type con los 4 siguienes digitos de c_payload i y c_payload i+1
                    for (int j = 0; j < 4; ++j) {
                        type[j] = c_payload[i + 2 + j];
                    }

                    if (type[0] == '0' && type[1] == '1' && type[2] == '1' && type[3] == '0') {
                        posicion = pos[i + 5];
                        for (int j = posicion; j <= posicion + 3; j++) {
                            stringBuilder.append(c_payload[j]);
                        }

                        //CALCULEMOS TEMP
                        char[] temp = stringBuilder.toString().toCharArray();
                        // Intercambiamos las posiciones 0 y 2, y 1 y 3
                        char temp0 = temp[0];
                        temp[0] = temp[2];
                        temp[2] = temp0;

                        char temp1 = temp[1];
                        temp[1] = temp[3];
                        temp[3] = temp1;

                        double airTemp = Hexadecimal(temp, 0, 4);
                        System.out.println("Temperature: " + airTemp / 1000 + " grados");
                        DATA3 = airTemp/1000;

                    }
                    if (type[0] == '0' && type[1] == '2' && type[2] == '1' && type[3] == '0') {
                        posicion = pos[i + 5];  //recorremos los 5 primeros caracteres para colocarnos en el 6 (contamos el 0)
                        for (int j = posicion; j <= posicion + 3; j++) {
                            stringBuilder.append(c_payload[j]);
                        }

                        //CALCULEMOS Hum
                        char[] hum = stringBuilder.toString().toCharArray();
                        // Intercambiamos las posiciones 0 y 2, y 1 y 3
                        char hum0 = hum[0];
                        hum[0] = hum[2];
                        hum[2] = hum0;

                        char hum1 = hum[1];
                        hum[1] = hum[3];
                        hum[3] = hum1;

                        double airHum = Hexadecimal(hum, 0, 4);
                        System.out.println("Humidity: " + airHum / 1000 + "%");
                        DATA4 = airHum/1000;

                        //CALCULAMOS CRC
                        //si la última posición es un 0 para que no de error se añade de forma manual 
                        if (c_payload[c_payload.length - 1] == '0') {
                            for (int j = posicion + 8; j <= posicion + 10; j++) {
                                if (c_payload[j] == '0' && c_payload[j + 1] == '0' && c_payload[j + 2] == '0' && c_payload[j + 3] == '7') {
                                    break;
                                } else {
                                    stringBuilder2.append(c_payload[j]);
                                }
                            }
                            String crc = stringBuilder2.toString();
                            if (!crc.isEmpty()) {
                                System.out.println("CRC: " + crc + "0");
                                DATA5 = crc;

                            }
                        } else {
                            for (int j = posicion + 8; j <= posicion + 11; j++) {
                                if (c_payload[j] == '0' && c_payload[j + 1] == '0' && c_payload[j + 2] == '0' && c_payload[j + 3] == '7') {
                                    break;
                                } else {
                                    stringBuilder2.append(c_payload[j]);
                                }
                            }
                            String crc = stringBuilder2.toString();
                            if (!crc.isEmpty()) {
                                System.out.println("CRC: " + crc);
                                DATA5 = crc;
                            }
                        }

                    }
                }
            }
            //iotib.añadirID(connection, "data", "sensecap_payload");
            int ID = iotib.obtenerXint(connection, "ID", "data");
            int counter = iotib.obtenerXint(connection, "counter", "data");
            preparedStatement = iotib.actualizarSenseCap(deveui, DATA1, DATA2, DATA3, DATA4, DATA5, counter, ID, "UPDATE sensecap_payload SET devEui = ?, batteryLevel = ?, uplinkInterval = ?, Temperature = ?, Humidity = ?, CRC = ?, Counter = ? WHERE ID = ?", connection);
            iotib.cerrarConexionConDatos(connection, preparedStatement);

        } else {
            iotib.cerrarConexionSinDatos(connection);
        }

    }
}
