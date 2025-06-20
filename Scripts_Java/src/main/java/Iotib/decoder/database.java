package Iotib.decoder;

import com.fasterxml.jackson.databind.JsonNode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class database {

    //Variables privadas 
    private final String URL;
    private final String username;
    private final String password;

    private PreparedStatement preparedStatement;

    ////////////////////////////////////////////////////////////////////////////
    /* Devuelve fecha en formato "yyyy-MM-dd HH:mm:ss" despues de haber pasado
       por parámetro fecha en formato ISO 8601 */
    private String conversionFechaISO(String trx_time) {
        Instant instant = Instant.parse(trx_time);
        LocalDateTime localDateTime = instant.atZone(ZoneId.of("UTC")).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String mysqlTimestamp = localDateTime.format(formatter);
        return mysqlTimestamp;
    }

    ////////////////////////////////////////////////////////////////////////////

    // Constructor de database
    public database(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }

    public PreparedStatement actualizarDragino(String deveui, int data0, String data1, int data2, int data3, int data4, String data5, String data6, String data7, String data8, double data9, int counter, int ID, String sql, Connection connection) throws SQLException {

        //"UPDATE dragino_payload SET devEui = ?, ocNumber = ?,ocStatus = ?, Alarm = ?, totalPulse = ?, lastOpen = ?, unixTime = ?, Model = ?, Version = ?, Frequency = ?, Battery = ?, Counter = ? WHERE ID = ?",
        //creamos objeto que se conecta a la base de datos mediante la sentencia SQL
        preparedStatement = connection.prepareStatement(sql);

        //rellenar DATA 
        preparedStatement.setString(1, deveui);
        preparedStatement.setInt(2, data0);
        preparedStatement.setString(3, data1);
        preparedStatement.setDouble(4, data2);
        preparedStatement.setDouble(5, data3);
        preparedStatement.setInt(6, data4);
        preparedStatement.setString(7, data5);
        preparedStatement.setString(8, data6);
        preparedStatement.setString(9, data7);
        preparedStatement.setString(10, data8);
        preparedStatement.setDouble(11, data9);
        preparedStatement.setInt(12, counter);
        preparedStatement.setInt(13, ID);

        //Ejecutar senténcia y cerrar objeto
        preparedStatement.executeUpdate();
        preparedStatement.close();

        return preparedStatement;   //representa una sentencia SQL preparada
    }

    public PreparedStatement actualizarMilesight(String deveui, int data0, String data1, String data2, String data3, String data4, String data5, int data6, double data7, double data8, int data9, int data10, int data11, int data12, double data13, int data14, int data15, String tipo_dec, int counter, int ID, Connection connection) throws SQLException {
        String sql;

        if ("FF".equals(tipo_dec)) {
            sql = "UPDATE milesight_payload SET devEui = ?, ProtocolVersion = ?, Device_SN = ?, HardwareVersion = ?, SoftwareVersion = ?, PowerOn = ?, DeviceClass = ?, Temperature = ?,Humidity = ?,Presence = ?,Light = ?,CO2 = ?,TVOC = ?, Presure = ?, tipo_dec = ?, Counter = ? WHERE ID = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, deveui);
            preparedStatement.setString(2, data1);
            preparedStatement.setString(3, data2);
            preparedStatement.setString(4, data3);
            preparedStatement.setString(5, data4);
            preparedStatement.setString(6, data5);
            preparedStatement.setInt(7, data6);
            preparedStatement.setDouble(8, data7);
            preparedStatement.setDouble(9, data8);
            preparedStatement.setInt(10, data9);
            preparedStatement.setInt(11, data10);
            preparedStatement.setInt(12, data11);
            preparedStatement.setInt(13, data12);
            preparedStatement.setDouble(14, data13);
            preparedStatement.setString(15, tipo_dec);
            preparedStatement.setInt(16, counter);
            preparedStatement.setInt(17, ID);

            preparedStatement.executeUpdate();
        } else if ("01".equals(tipo_dec)) {
            sql = "UPDATE milesight_payload SET devEui = ?, BatteryLevel = ?, Temperature = ?,Humidity = ?,Presence = ?, valorPresencia =?, HayPresencia = ?, Light = ?,CO2 = ?,TVOC = ?, Presure = ?, VisibilityInfrareed = ?, Infrareed = ?, tipo_dec = ?, Counter = ? WHERE ID = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, deveui);
            preparedStatement.setInt(2, data0);
            preparedStatement.setDouble(3, data7);
            preparedStatement.setDouble(4, data8);
            preparedStatement.setInt(5, data9);
            preparedStatement.setString(6, data5);
            preparedStatement.setInt(7, data6);
            preparedStatement.setInt(8, data10);
            preparedStatement.setInt(9, data11);
            preparedStatement.setInt(10, data12);
            preparedStatement.setDouble(11, data13);
            preparedStatement.setInt(12, data14);
            preparedStatement.setInt(13, data15);
            preparedStatement.setString(14, tipo_dec);
            preparedStatement.setInt(15, counter);
            preparedStatement.setInt(16, ID);

            preparedStatement.executeUpdate();
        } else if ("03".equals(tipo_dec)) {
            sql = "UPDATE milesight_payload SET devEui = ?, Temperature = ?,Humidity = ?,Presence = ?, ValorPresencia =?, HayPresencia = ?, Light = ?,CO2 = ?,TVOC = ?, Presure = ?, VisibilityInfrareed = ?, Infrareed = ?, tipo_dec = ?, Counter = ? WHERE ID = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, deveui);
            preparedStatement.setDouble(2, data7);
            preparedStatement.setDouble(3, data8);
            preparedStatement.setInt(4, data9);
            preparedStatement.setString(5, data5);
            preparedStatement.setInt(6, data6);
            preparedStatement.setInt(7, data10);
            preparedStatement.setInt(8, data11);
            preparedStatement.setInt(9, data12);
            preparedStatement.setDouble(10, data13);
            preparedStatement.setInt(11, data14);
            preparedStatement.setInt(12, data15);
            preparedStatement.setString(13, tipo_dec);
            preparedStatement.setInt(14, counter);
            preparedStatement.setInt(15, ID);

            preparedStatement.executeUpdate();
        } else if ("08".equals(tipo_dec)) {
            sql = "UPDATE milesight_payload SET devEui = ?, TVOC = ?, tipo_dec = ?, Counter = ? WHERE ID = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, deveui);
            preparedStatement.setInt(2, data12);
            preparedStatement.setString(3, tipo_dec);
            preparedStatement.setInt(4, counter);
            preparedStatement.setInt(5, ID);

            preparedStatement.executeUpdate();

        } else {
            // Lógica para otros casos si es necesario
            throw new IllegalArgumentException("Tipo de decodificación no válido: " + tipo_dec);
        }

        // Cerrar el PreparedStatement después de ejecutar la actualización
        preparedStatement.close();

        return preparedStatement; // Esto puede ser problemático, considera cambiar el tipo de retorno o devolver null
    }

    public PreparedStatement actualizarSenseCap(String deveui, int data1, int data2, double data3, double data4, String data5, int counter, int ID, String sql, Connection connection) throws SQLException {

        //creamos objeto que se conecta a la base de datos mediante la sentencia SQL
        preparedStatement = connection.prepareStatement(sql);

        //rellenar DATA 
        preparedStatement.setString(1, deveui);
        preparedStatement.setInt(2, data1);
        preparedStatement.setDouble(3, data2);
        preparedStatement.setDouble(4, data3);
        preparedStatement.setDouble(5, data4);
        preparedStatement.setString(6, data5);
        preparedStatement.setInt(7, counter);
        preparedStatement.setInt(8, ID);

        //Ejecutar senténcia y cerrar objeto
        preparedStatement.executeUpdate();
        preparedStatement.close();

        return preparedStatement;   //representa una sentencia SQL preparada
    }

    //Metodo para ir llenando la tabla payload de la base de datos 
    public PreparedStatement actualizarPayload(String deveui, int data1, double data2, double data3, int data4, int data5, int data6, int data7, int data8, int data9, double data10, int data11, int data12, int data13, String data14, String data15, int data16, int ID, String sql, Connection connection) throws SQLException {

        //creamos objeto que se conecta a la base de datos mediante la sentencia SQL
        preparedStatement = connection.prepareStatement(sql);

        //rellenar DATA 
        preparedStatement.setString(1, deveui);
        preparedStatement.setInt(2, data1);
        preparedStatement.setDouble(3, data2);
        preparedStatement.setDouble(4, data3);
        preparedStatement.setInt(5, data4);
        preparedStatement.setInt(6, data5);
        preparedStatement.setInt(7, data6);
        preparedStatement.setInt(8, data7);
        preparedStatement.setInt(9, data8);
        preparedStatement.setInt(10, data9);
        preparedStatement.setDouble(11, data10);
        preparedStatement.setInt(12, data11);
        preparedStatement.setInt(13, data12);
        preparedStatement.setInt(14, data13);
        preparedStatement.setString(15, data14);
        preparedStatement.setString(16, data15);
        preparedStatement.setInt(17, data16);
        preparedStatement.setInt(18, ID);

        //Ejecutar senténcia y cerrar objeto
        preparedStatement.executeUpdate();
        preparedStatement.close();

        return preparedStatement;   //representa una sentencia SQL preparada
    }

    //Método para insertar los dados en la tabla data de la base de datos
    public PreparedStatement insertDataGateway(JsonNode packet, String payload, String sql, Connection connection) throws SQLException {

        //Se extraen los valores del paquete y se almacenan en variables
        String DevEUI = packet.get("deveui").asText();
        String AppEUI = packet.get("appeui").asText();
        String CodeRate = packet.get("coderate").asText();
        String DataRate = packet.get("datarate").asText();
        String Frequency = packet.get("frequency").asText();
        String Port = packet.get("port").asText();
        int RSSI = packet.get("rssi").asInt();
        int SNR = packet.get("snr").asInt();
        String GWEUI = packet.get("gweui").asText();
        String trx_time = packet.get("trx_time").asText();
        String counter = packet.get("counter").asText();
        String direction = packet.get("direction").asText();

        int COUNTER = Integer.parseInt(counter, 16);

        ////// CONVERSIÓN FORMATO ISO 8601 A yyyy-MM-dd HH:mm:ss ///////////////
        String mysqlTimestamp = conversionFechaISO(trx_time);
        ////////////////////////////////////////////////////////////////////////

        //creamos objeto que se conecta a la base de datos mediante la sentencia SQL
        preparedStatement = connection.prepareStatement(sql);

        //rellenar DATA con las variables anteriores 
        preparedStatement.setString(1, DevEUI);
        preparedStatement.setString(2, AppEUI);
        preparedStatement.setString(3, CodeRate);
        preparedStatement.setString(4, DataRate);
        preparedStatement.setString(5, Frequency);
        preparedStatement.setString(6, Port);
        preparedStatement.setInt(7, RSSI);
        preparedStatement.setInt(8, SNR);
        preparedStatement.setString(9, GWEUI);
        preparedStatement.setString(10, mysqlTimestamp);
        preparedStatement.setString(11, payload);
        preparedStatement.setInt(12, COUNTER);
        preparedStatement.setString(13, direction);

        //Ejecutar senténcia y cerrar objeto
        preparedStatement.executeUpdate();
        preparedStatement.close();

        return preparedStatement;   //representa una sentencia SQL preparada
    }

    //Método para agregar ID de data a payload
    public void añadirID(Connection connection, String tablaOrigen, String tablaDestino) throws SQLException {
        int ID = obtenerXint(connection, "ID", tablaOrigen); //obtenemos valor

        //Ruta para insertar valor
        String sql_payload = "INSERT INTO " + tablaDestino + " (ID) VALUES (?)";
        preparedStatement = connection.prepareStatement(sql_payload);
        preparedStatement.setInt(1, ID);    //insertamos valor    
        preparedStatement.executeUpdate();

    }

    //Método para establecer conexión con la base de datos
    public Connection abrirConexion() throws SQLException {
        String URL_COMPLETA = URL + "?username=" + username + "&password=" + password;
        Connection connection = DriverManager.getConnection(URL_COMPLETA, username, password);

        return connection;
    }

    ////////////////////////////////////////////////////////////////////////////
    /* Método para cerrar la conexión en caso de haber alguna sentencia SQL preparada */
    public void cerrarConexionConDatos(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
        connection.close();
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /* Método para cerrar la conexión en caso de NO haber alguna sentencia SQL preparada */
    public void cerrarConexionSinDatos(Connection connection) throws SQLException {
        connection.close();
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /* Devuelve el último (de la última fila) int despues de haberlo solicitado a
    una base de datos, hay que pasar por parámetro la columna y la tabla de donde
    queremos obtener el dato. */
    public int obtenerXint(Connection connection, String columna, String table) throws SQLException {

        //Creamos sentencia seleciona y ordena los resultados
        PreparedStatement obtenerXint = connection.prepareStatement("SELECT " + columna + " FROM " + table + " ORDER BY ID DESC LIMIT 1");

        ResultSet resultSet = obtenerXint.executeQuery();   //ejecutar consulta

        int xInt = 0;
        if (resultSet.next()) { //miramos si hay valor en resultSet
            xInt = resultSet.getInt(1);
        }

        return xInt;
    }

    ////////////////////////////////////////////////////////////////////////////
    /* Devuelve el último (de la última fila) double despues de haberlo solicitado a
    una base de datos, hay que pasar por parámetro la columna y la tabla de donde
    queremos obtener el dato. */
    public double obtenerXdouble(Connection connection, String columna, String table) throws SQLException {

        //Creamos sentencia seleciona y ordena los resultados
        PreparedStatement obtenerXdouble = connection.prepareStatement("SELECT " + columna + " FROM " + table + " ORDER BY ID DESC LIMIT 1");

        ResultSet resultSet = obtenerXdouble.executeQuery();    //ejecutar consulta

        double xDouble = 0;
        if (resultSet.next()) { //miramos si hay valor en resultSet
            xDouble = resultSet.getDouble(1);
        }

        return xDouble;
    }

    ////////////////////////////////////////////////////////////////////////////
    //Método para obtener la última String de un tabla 
    public String obtenerXstring(Connection connection, String columna, String table) throws SQLException {

        //Creamos sentencia seleciona y ordena los resultados
        PreparedStatement obtenerXstring = connection.prepareStatement("SELECT " + columna + " FROM " + table + " ORDER BY ID DESC LIMIT 1");

        ResultSet resultSet = obtenerXstring.executeQuery();    //ejecutar consulta

        String xString = null;
        if (resultSet.next()) { //miramos si hay valor en resultSet
            xString = resultSet.getString(1);
        }

        return xString;
    }

    //Método para obtener la primera String de un tabla 
    public String obtener1Xstring(Connection connection, String columna, String table) throws SQLException {

        //Creamos sentencia seleciona 
        PreparedStatement obtenerXstring = connection.prepareStatement("SELECT " + columna + " FROM " + table + " ORDER BY " + columna + " DESC");

        ResultSet resultSet = obtenerXstring.executeQuery();    //ejecutar consulta

        String xString = null;
        if (resultSet.next()) { //miramos si hay valor en resultSet
            xString = resultSet.getString(1);
        }

        return xString;
    }

    //////////////////////////////////////////////////////////////////
    public PreparedStatement actualizarAdeunis(String deveui, int data1, int data2, String datax, String datay, String dataz, int data3, int data4, int data5, int data6, int data7, int data8, String data9, String tipo_dec, int counter, int ID, Connection connection) throws SQLException {
        String sql;

        if ("10".equals(tipo_dec)) {
            sql = "UPDATE adeunis_payload SET dev_Eui = ?, FrameCounter = ?, StatusByte = ?,TRX_KeepAlive = ?,SamplingPeriod = ?,InhibitionTime = ?,TRX_Periodic = ?,HistoryPeriod = ?,formattype = ?, Counter = ? WHERE ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, deveui);
            preparedStatement.setInt(2, data1);
            preparedStatement.setInt(3, data2);
            preparedStatement.setString(4, datax);
            preparedStatement.setString(5, datay);
            preparedStatement.setString(6, dataz);
            preparedStatement.setInt(7, data3);
            preparedStatement.setInt(8, data4);
            preparedStatement.setString(9, tipo_dec);
            preparedStatement.setInt(10, counter);
            preparedStatement.setInt(11, ID);
            preparedStatement.executeUpdate();
        } else if ("1F".equals(tipo_dec)) {
            sql = "UPDATE adeunis_payload SET dev_Eui = ?, FrameCounter = ?,StatusByte=?, AlarmButton1Configuration = ?,DeboundState1 = ?,ThresholdButton1 = ?,AlarmButton2Configuration = ?,DeboundState2 = ?,ThresholdButton2 = ?,formattype = ?, Counter = ? WHERE ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, deveui);
            preparedStatement.setInt(2, data1);
            preparedStatement.setInt(3, data2);
            preparedStatement.setInt(4, data3);
            preparedStatement.setInt(5, data4);
            preparedStatement.setInt(6, data5);
            preparedStatement.setInt(7, data6);
            preparedStatement.setInt(8, data7);
            preparedStatement.setInt(9, data8);
            preparedStatement.setString(10, tipo_dec);
            preparedStatement.setInt(11, counter);
            preparedStatement.setInt(12, ID);
            preparedStatement.executeUpdate();
        } else if ("20".equals(tipo_dec)) {
            sql = "UPDATE adeunis_payload SET dev_Eui = ?, FrameCounter = ?,StatusByte=?, DutyCycle = ?,ADR = ?,OTAA = ?,ABP = ?,formattype = ?, Counter = ? WHERE ID = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, deveui);
            preparedStatement.setInt(2, data1);
            preparedStatement.setInt(3, data2);
            preparedStatement.setInt(4, data3);
            preparedStatement.setInt(5, data4);
            preparedStatement.setInt(6, data5);
            preparedStatement.setInt(7, data6);
            preparedStatement.setString(8, tipo_dec);
            preparedStatement.setInt(9, counter);
            preparedStatement.setInt(10, ID);

            preparedStatement.executeUpdate();
        } else if ("5C".equals(tipo_dec)) {
            sql = "UPDATE adeunis_payload SET dev_Eui = ?, FrameCounter = ?,StatusByte=?, PresenceState = ?, AlarmState =?, Presence = ?,Luminosity = ?,formattype=?,Counter = ? WHERE ID = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, deveui);
            preparedStatement.setInt(2, data1);
            preparedStatement.setInt(3, data2);
            preparedStatement.setInt(4, data3);
            preparedStatement.setString(5, data9);
            preparedStatement.setInt(6, data4);
            preparedStatement.setInt(7, data5);
            preparedStatement.setString(8, tipo_dec);
            preparedStatement.setInt(9, counter);
            preparedStatement.setInt(10, ID);

            preparedStatement.executeUpdate();
        } else if ("5D".equals(tipo_dec)) {
            sql = "UPDATE adeunis_payload SET dev_Eui = ?, FrameCounter = ?,StatusByte=?, PresenceState = ?, AlarmState =?, Luminosity = ?,formattype = ?, Counter = ? WHERE ID = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, deveui);
            preparedStatement.setInt(2, data1);
            preparedStatement.setInt(3, data2);
            preparedStatement.setInt(4, data3);
            preparedStatement.setString(5, data9);
            preparedStatement.setInt(6, data4);
            preparedStatement.setString(7, tipo_dec);
            preparedStatement.setInt(8, counter);
            preparedStatement.setInt(9, ID);

            preparedStatement.executeUpdate();
        } else {
            // Lógica para otros casos si es necesario
            throw new IllegalArgumentException("Tipo de decodificación no válido: " + tipo_dec);
        }

        preparedStatement.close();

        return preparedStatement;
    }
}
