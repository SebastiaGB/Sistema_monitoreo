package Iotib.decoder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class AdeunisPayload {

    static private char[] c_payload;
    private static final String URL = "jdbc:mysql://localhost:3306/iotib2";
    private static final database iotib = new database(URL, "root", "root");
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static String DATAX;
    private static String DATAY;
    private static String DATAZ;
    private int DATA1 = 0;
    private int DATA2 = 0;
    private int DATA3 = 0;
    private int DATA4 = 0;
    private int DATA5 = 0;
    private int DATA6 = 0;
    private int DATA7 = 0;
    private int DATA8 = 0;
    private String DATA9 = null;
    private final String deveui;

    //////////////////////////////////////////////////////////////////////////
    ///////////////////STATUS BYTE/////////////////////////////7
    private void StatusByte1() throws SQLException {

        if (c_payload[2] >= 48 && c_payload[2] <= 57) {
            String binario = Integer.toBinaryString(c_payload[2] - 48);

            while (binario.length() < 4) {
                binario = "0" + binario;
            }

            int c_binario[] = new int[4];

            for (int i = 0; i < binario.length(); i++) {
                c_binario[i] = (binario.charAt(i) - 48);
            }

            int bin_to_dec = ((int) Math.pow(2, 2) * c_binario[0]) + (2 * c_binario[1]) + (c_binario[2]);

            System.out.println("Status byte:");
            System.out.println("Trama número " + bin_to_dec);
            DATA1 = bin_to_dec; //datos 1 de payload en integer

        } else {
            String hexadecimal = new String(c_payload, 2, 1);
            int decimal = Integer.parseInt(hexadecimal, 16);

            String binario = Integer.toBinaryString(decimal);

            while (binario.length() < 4) {
                binario = "0" + binario;
            }

            int c_binario[] = new int[4];

            for (int i = 0; i < binario.length(); i++) {
                c_binario[i] = (binario.charAt(i) - 48);
            }

            int bin_to_dec = ((int) Math.pow(2, 2) * c_binario[0]) + (2 * c_binario[1]) + (c_binario[2]);
            System.out.println("Status byte:");
            System.out.println("Trama número " + bin_to_dec);
            DATA1 = bin_to_dec;   //datos 1 de payload en integer

        }
    }

    private void StatusByte2() throws SQLException {

        switch (c_payload[3]) {
            case '0' -> {
                System.out.println("Bit1@" + c_payload[3] + ": No Error");
                DATA2 = 0;
            }
            case '1' -> {
                System.out.println("Bit1@" + c_payload[3] + ": Configuration Done");
                DATA2 = 1;
            }
            case '2' -> {
                System.out.println("Bit1@" + c_payload[3] + ": Low bat");
                DATA2 = 2;
            }
            case '4' -> {
                System.out.println("Bit1@" + c_payload[3] + ": HW Error");
                DATA2 = 3;
            }
            case '8' -> {
                System.out.println("Bit1@" + c_payload[3] + ": AppFlag1");
                DATA2 = 4;
            }
            default -> {
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////7
    ///////////////////////////CONVERSIONS METHODS///////////////////////////////
    //conversión a hexadecimal
    private static int Hexadecimal(int x, int y) {

        String hexadecimal = new String(c_payload, x, y);
        int decimal = Integer.parseInt(hexadecimal, 16);

        return decimal;
    }

    //conversión a horas
    private static int Horas(int decimal) {
        int horas = decimal / 3600;
        return horas;
    }

    //conversión minutos
    private static int Minutos(int decimal) {
        int minutos = (decimal % 3600) / 60;
        return minutos;
    }

    //conversión a segundos
    private static int Segundos(int decimal) {
        int segundos = decimal % 60;
        return segundos;
    }

    //conversión a binario 
    private static String Binario(int bitnum) {

        if (c_payload[bitnum] >= 48 && c_payload[bitnum] <= 57) {
            String binario = Integer.toBinaryString(c_payload[bitnum] - 48);

            while (binario.length() < 4) {
                binario = "0" + binario;
            }
            return binario;

        } else {

            String binario = Integer.toBinaryString(Hexadecimal(2, 1));

            while (binario.length() < 4) {
                binario = "0" + binario;
            }
            return binario;
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    ///////////////////APLICATION METHODS////////////////////7

    //CONFIGURACIÓN DEL BOTÓN DE ALARMA
    private void AlarmButton(int buttonNumber, int bitNumber) {

        switch (Binario(bitNumber)) {
            case ("0010") -> {
                System.out.println("The Configuration of the button alarm " + buttonNumber + " is: ");
                System.out.println("Event OFF");
                if (bitNumber == 11) {
                    DATA6 = 0; //off
                } else {
                    DATA3 = 0; //off
                }
            }
            case ("0001") -> {
                System.out.println("The Configuration of the button alarm " + buttonNumber + " is: ");
                System.out.println("Event ON");
                if (bitNumber == 11) {
                    DATA6 = 1;  //on
                } else {
                    DATA3 = 1;  //on
                }
            }
            case ("0000") -> {
                System.out.println("The Configuration of the button alarm " + buttonNumber + " is: ");
                System.out.println("desactivated");
                if (bitNumber == 11) {
                    DATA6 = 3;  //desactivada
                } else {
                    DATA3 = 3;
                }
            }
            default -> {
                System.out.println("The Configuration of the button alarm " + buttonNumber + " is: ");
                System.out.println("Event ON/OFF");
                if (bitNumber == 11) {
                    DATA6 = 2; //On/off
                } else {
                    DATA3 = 2;
                }
            }
        }

    }

    //CONFIGURACIÓN DEL TIMEPO DE DEBOUND
    private void DeboundTime(int bitNumber) {

        if (bitNumber == 4) {
            switch (c_payload[bitNumber]) {
                case '0' -> {
                    System.out.println("no debound@");
                    DATA4 = 0;// "No debound";
                }
                case '1' -> {
                    System.out.println("debound@10 ms");
                    DATA4 = 1000;// "Debound@10 ms";
                }
                case '2' -> {
                    System.out.println("debound@20 ms");
                    DATA4 = 2000;// "Debound@20 ms";
                }
                case '3' -> {
                    System.out.println("debound@50 ms");
                    DATA4 = 5000;// "Debound@50 ms";
                }
                case '4' -> {
                    System.out.println("debound@100 ms");
                    DATA4 = 1000;// "Debound@100 ms";
                }
                case '5' -> {
                    System.out.println("debound@200 ms");
                    DATA4 = 2000;// "Debound@200 ms";
                }
                case '6' -> {
                    System.out.println("debound@500 ms");
                    DATA4 = 5000;// "Debound@500 ms";
                }
                case '7' -> {
                    System.out.println("debound@1 s");
                    DATA4 = 1; //"Debound@1 ms";
                }
                case '8' -> {
                    System.out.println("debound@2 s");
                    DATA4 = 2;//"Debound@2 s";
                }
                case '9' -> {
                    System.out.println("debound@5 s");
                    DATA4 = 5; //"Debound@5 s";
                }
                case 'A' -> {
                    System.out.println("debound@10 s");
                    DATA4 = 10; //"Debound@10 s";
                }
                case 'B' -> {
                    System.out.println("debound@20 s");
                    DATA4 = 20; // "Debound@20 s";
                }
                case 'C' -> {
                    System.out.println("debound@40 s");
                    DATA4 = 40; // "Debound@40 s";
                }
                case 'D' -> {
                    System.out.println("debound@60 s");
                    DATA4 = 60; // "Debound@60 s";
                }
                case 'E' -> {
                    System.out.println("debound@5 min");
                    DATA4 = 300; // "Debound@5 min";
                }
                case 'F' -> {
                    System.out.println("debound@10 min");
                    DATA4 = 600; //"Debound@10 min";
                }
                default -> {
                }
            }
        } else {

            switch (c_payload[bitNumber]) {
                case '0' -> {
                    System.out.println("no debound@");
                    DATA7 = 0;// "No debound";
                }
                case '1' -> {
                    System.out.println("debound@10 ms");
                    DATA7 = 1000;// "Debound@10 ms";
                }
                case '2' -> {
                    System.out.println("debound@20 ms");
                    DATA7 = 2000;// "Debound@20 ms";
                }
                case '3' -> {
                    System.out.println("debound@50 ms");
                    DATA7 = 5000;// "Debound@50 ms";
                }
                case '4' -> {
                    System.out.println("debound@100 ms");
                    DATA7 = 1000;// "Debound@100 ms";
                }
                case '5' -> {
                    System.out.println("debound@200 ms");
                    DATA7 = 2000;// "Debound@200 ms";
                }
                case '6' -> {
                    System.out.println("debound@500 ms");
                    DATA7 = 5000;// "Debound@500 ms";
                }
                case '7' -> {
                    System.out.println("debound@1 s");
                    DATA7 = 1; //"Debound@1 ms";
                }
                case '8' -> {
                    System.out.println("debound@2 s");
                    DATA7 = 2;//"Debound@2 s";
                }
                case '9' -> {
                    System.out.println("debound@5 s");
                    DATA7 = 5; //"Debound@5 s";
                }
                case 'A' -> {
                    System.out.println("debound@10 s");
                    DATA7 = 10; //"Debound@10 s";
                }
                case 'B' -> {
                    System.out.println("debound@20 s");
                    DATA7 = 20; // "Debound@20 s";
                }
                case 'C' -> {
                    System.out.println("debound@40 s");
                    DATA7 = 40; // "Debound@40 s";
                }
                case 'D' -> {
                    System.out.println("debound@60 s");
                    DATA7 = 60; // "Debound@60 s";
                }
                case 'E' -> {
                    System.out.println("debound@5 min");
                    DATA7 = 300; // "Debound@5 min";
                }
                case 'F' -> {
                    System.out.println("debound@10 min");
                    DATA7 = 600; //"Debound@10 min";
                }
                default -> {
                }
            }
        }

    }

    //INICIO DEL ESTADO ACTUAL Y PASADO
    private void AlarmState(int bitNumber1, int bitNumber2) {

        int c_binario2[] = new int[4];

        for (int i = 0; i < Binario(5).length(); i++) {
            c_binario2[i] = (Binario(5).charAt(i) - 48);
        }

        if (c_binario2[bitNumber1] == 1) {
            System.out.println("Current state: ON/CLOSED");
            DATA4 = 1; //"ON/CLOSED";
        } else {
            System.out.println("Current state: OFF/OPENED");
            DATA4 = 0; // "OFF/OPENED";
        }
        if (c_binario2[bitNumber2] == 1) {
            System.out.println("Previous state: ON/CLOSED");
            DATA5 = 1; // "ON/CLOSED";
        } else {
            System.out.println("Previous state: OFF/OPENED");
            DATA5 = 0; // "OFF/OPENED";
        }
    }

    //INICIO DEL DUTY CYCLE Y ADR
    private void DutyCycleAndADR(int bitNumber1, int bitNumber2) {

        int c_binario[] = new int[4];

        for (int i = 0; i < Binario(5).length(); i++) {
            c_binario[i] = (Binario(5).charAt(i) - 48);
        }

        if (c_binario[bitNumber2] == 1) {
            System.out.println("Duty Cycle Activated");
            DATA3 = 1; //"Duty Cycle Activated";
        } else {
            System.out.println("Duty Cycle Desactivated");
            DATA3 = 0; // "Duty Cycle Desactivated";
        }
        if (c_binario[bitNumber1] == 1) {
            System.out.println("ADR ON");
            DATA4 = 1; // "ADR ON";
        } else {
            System.out.println("ADR OFF");
            DATA4 = 0; // "ADR OFF";
        }
    }//FINAL DEL DUTY CYCLE Y ADR 

    //INICIO DE LA PRESNEICA 
    private void Presence(int bitNum) {

        if (c_payload[bitNum] == '1') {
            System.out.println("Presencia detectada");
            DATA9 = "Hay Presencia";
            DATA3 = 1; //"Presencia detectada";
        } else {
            System.out.println("Se ha dejado de detectar presencia");
            DATA9 = "No Hay Presencia";
            DATA3 = 0; // "Se ha dejado de detectar presencia";
        }
    }

    //INICIO DE LA PRESENCIA + LUMINOSIDAD
    private void PresAndLum(char[] payload) {
        int contador = (payload.length - 6) / 4;
        for (int i = 0; i < contador; i++) {
            int periodo = i + 1;
            int decimal_1 = Hexadecimal(6 + 4 * i, 2);
            int decimal_2 = Hexadecimal(8 + 4 * i, 2);

            System.out.println("Durante el periodo " + periodo + " se ha detectado una presencia el " + decimal_1 + " % del tiempo");
            DATA4 = decimal_1; //" % del tiempo";
            System.out.println("La luminosidad medida ha sido de un " + decimal_2 + " %");
            DATA5 = decimal_2;
            System.out.println("");
        }

    }


// Constructor clase AdeunisPayload
    public AdeunisPayload(String payload, Connection connection) throws SQLException {
        c_payload = payload.toCharArray();
        this.deveui = iotib.obtenerXstring(connection, "DEV_EUI", "data");
    }

    /////////////////////////////////////////////////////////////////////////////
    ///////////////////////MAIN CODE/////////////////////////////777
    // INICIO RECORRER FRAME CODE 
    public void FrameCode() throws SQLException {

        connection = iotib.abrirConexion();

        String direction = iotib.obtenerXstring(connection, "Direction", "data");
        int puerto = iotib.obtenerXint(connection, "port", "data");
        String tipo_dec = new String(new char[]{c_payload[0], c_payload[1]});

        if ("up".equals(direction) && puerto != 0) {

                switch (tipo_dec) {
                    case ("10"):
                        System.out.println("Frame code : Product configuration");
                        //Inicio del 1r Byte
                        StatusByte1();
                        StatusByte2();

                        //Inicio del 2ndo al 3r Byte
                        int decimal1 = Hexadecimal(4, 4) * 10;
                        System.out.println("The Transmission period of the Keep Alive frame is " + Horas(decimal1) + " h " + Minutos(decimal1) + " min " + Segundos(decimal1) + " sec");

                        DATAX = Horas(decimal1) + "h " + Minutos(decimal1) + "min " + Segundos(decimal1) + "sec";

                        //Inicio del 4rto al 5nto Byte 
                        System.out.println("The Transmission period of the periodic frame is " + Hexadecimal(8, 4));

                        DATA3 = (Hexadecimal(8, 4));

                        //Inicio del 6xto al septimo byte 
                        System.out.println("The History period is " + Hexadecimal(12, 4));

                        DATA4 = (Hexadecimal(12, 4));

                        //Inicio del 8vo al 9no Byte
                        int decimal2 = Hexadecimal(16, 4) * 2;
                        System.out.println("The Sampling period is " + Horas(decimal2) + "h " + Minutos(decimal2) + "min " + Segundos(decimal2) + "sec");
                        //Final 8vo al 9no byte

                        DATAY = Horas(decimal1) + " h" + Minutos(decimal1) + " min" + Segundos(decimal1) + " sec";
                        //Inicio del decimo al 11avo Byte 
                        int decimal3 = Hexadecimal(20, 4) * 10;
                        System.out.println("The Sampling period is " + Horas(decimal3) + "h " + Minutos(decimal3) + "min " + Segundos(decimal3) + "sec");
                        //Final del decimo al 11avo Byte 
                        DATAZ = Horas(decimal1) + " h" + Minutos(decimal1) + " min" + Segundos(decimal1) + " sec";
                        break;
                    case ("1F"):
                        System.out.println("Frame code : Digital inputs configuration");

                        //Inicio del 1r Byte
                        StatusByte1();
                        StatusByte2();

                        //Inicio del 2ndo Byte
                        AlarmButton(1, 5);
                        DeboundTime(4);

                        //Inicio del 3 al 4rto Byte
                        System.out.println("Alarm triggered after " + Hexadecimal(6, 4) + " detection ");
                        DATA5 = (Hexadecimal(6, 4));

                        //Inicio del 5 Byte
                        AlarmButton(2, 11);
                        DeboundTime(10);

                        //Inicio del 6xto al septimo Byte
                        System.out.println("The Threshold of digital input 2 alarm is: " + Hexadecimal(12, 4));
                        DATA8 = (Hexadecimal(12, 4));

                        break;
                    case ("20"):
                        System.out.println("Frame code : Network configuration");

                        //Inicio del 1r Byte
                        StatusByte1();
                        StatusByte2();

                        //Inicio del 2ndo Byte
                        DutyCycleAndADR(3, 1);

                        //Inico del 3r Byte 
                        if (c_payload[7] == '1') {
                            System.out.println("OTAA");
                            DATA5 = 1;  //si data 5 es 1 - OtAA
                        } else {
                            System.out.println("ABP");
                            DATA6 = 1; //si data6 es 1 - ABP
                        }
                        break;

                    case ("5C"):
                        System.out.println("Modo: trama periódica de datos");

                        //Inicio del 1r Byte
                        StatusByte1();
                        StatusByte2();

                        // Inicio del 2ndo Byte
                        Presence(5);    //al enviar la trama

                        //Inicio del resto de Bytes
                        PresAndLum(c_payload);

                        break;

                    case ("5D"):
                        System.out.println("Modo: Alarma de presencia");

                        //Inicio del 1r Byte
                        StatusByte1();
                        StatusByte2();

                        //Inicio del 2ndo Byte
                        Presence(5);    //alarma 

                        //Inicio del 3r Byte
                        System.out.println("La luminosidad es de un " + Hexadecimal(6, 2) + " %");
                        DATA4 = Hexadecimal(6, 2);

                        break;
                    default:
                        break;
              //  }
            }

            //iotib.añadirID(connection, "data", "adeunis_payload");
            int ID = iotib.obtenerXint(connection, "ID", "data");
            int counter = iotib.obtenerXint(connection, "counter", "data");
            preparedStatement = iotib.actualizarAdeunis(deveui, DATA1, DATA2, DATAX, DATAY, DATAZ, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, tipo_dec, counter, ID, connection);
            iotib.cerrarConexionConDatos(connection, preparedStatement);
        } else {
            iotib.cerrarConexionSinDatos(connection);
        }
    }
}
