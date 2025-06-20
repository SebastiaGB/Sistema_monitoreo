package Iotib.decoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Gateway_prueba {

    private final String baseUrl;
    private final ObjectMapper objectMapper;
    private static final String URL = "jdbc:mysql://localhost:3306/iotib2";
    private static final database iotib = new database(URL, "root", "root");
    private static Connection connection;

    ////////////////////////////////////////////////////////////////////////////
    /* Con este método configuramos el SSL necesario para realizar la conexión con 
       el gateway. */
    private void configureSSL() throws Exception {
        TrustManager[] trustAllCertificates;
        trustAllCertificates = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    /* Devuelve una String con la representación hexadecimal de bytes pasados por
       parámetro. */
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    /* Convertimos fecha proveniente de archivo JSON (formato ISO 8601) a formato
       LocalDateTime para poder realizar comparaciones de fechas. */
    private LocalDateTime conversionFechaISO(String trx_time) {
        LocalDateTime localDateTime = LocalDateTime.parse(trx_time,
                DateTimeFormatter.ISO_DATE_TIME);
        return localDateTime;
    }
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    /* Convertimos fecha proveniente de database (formato "yyyy-MM-dd HH:mm:ss") 
    a formato LocalDateTime para poder realizar comparaciones de fechas. */
    private LocalDateTime conversionFechaNormal(String trx_time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM"
                + "-dd HH:mm:ss");
        return LocalDateTime.parse(trx_time, formatter);
    }
    ////////////////////////////////////////////////////////////////////////////

    /////////// CONSTRUCTOR ///////////
    /* Le pasamos por parámetro la dirección IP del gateway. */
    public Gateway_prueba(String baseURL) {
        this.baseUrl = baseURL;
        this.objectMapper = new ObjectMapper();
    }
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    /* Devuelve una String con el token necesario para realizar el resto de
       peticiones al gateway. Primeramente le pasamos por parámetros el usuario y
       contraseña del gateway, se realiza una petición POST y el gateway contesta
       con el token deseado. */
    public String obtainToken(String username, String password) throws IOException,
            Exception {
        configureSSL();

        String loginRequestBody = "{\"username\": \"" + username + "\", \"password\":"
                + " \"" + password + "\"}";
        String loginUrl = baseUrl + "/api/login";

        HttpURLConnection connection = (HttpURLConnection) new URL(loginUrl).
                openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try ( OutputStream os = connection.getOutputStream()) {
            byte[] input = loginRequestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int loginResponseCode = connection.getResponseCode();
        if (loginResponseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader loginReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder loginResponse = new StringBuilder();

            while ((inputLine = loginReader.readLine()) != null) {
                loginResponse.append(inputLine);
            }

            loginReader.close();

            JsonNode jsonNode = objectMapper.readTree(loginResponse.toString());
            return jsonNode.get("result").get("token").asText();
        } else {
            throw new IOException("Inicio de sesión fallido con código de respuesta:"
                    + " " + loginResponseCode);
        }
    }
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    /* Este método se encarga de gestionar datos obtenidos a través de una 
       solicitud HTTP. Para ello, se autentica mediante un token proporcionado. 
       Luego, se compara la información recibida con registros previamente 
       almacenados en una base de datos. Dependiendo de los valores y condicione
       específicas de ciertos campos, el método realiza operaciones específicas, 
        como la conversión de datos, inserción en la base de datos y procesamiento
        adicional de los paquetes de datos.*/
    public void paquetesPorCampos(String token) throws IOException, SQLException {
        String packetsUrl = baseUrl + "/api/lora/packets?token=" + token;
        Map<String, LocalDateTime> fieldValues = new HashMap<>();

        connection = iotib.abrirConexion();

        String last_trx_time = iotib.obtener1Xstring(connection, "trx_time", "data");
        LocalDateTime fechaDatabase = null;
        if (last_trx_time != null) {
            boolean acabado = false;

            fechaDatabase = conversionFechaNormal(last_trx_time);

            fieldValues.put("trx_time", fechaDatabase);

            HttpURLConnection connection_http = (HttpURLConnection) new URL(
                    packetsUrl).openConnection();
            connection_http.setRequestMethod("GET");

            int responseCode = connection_http.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection_http.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }

                reader.close();

                String packets = response.toString();
                JsonNode jsonNode = objectMapper.readTree(packets);

                for (JsonNode packet : jsonNode.get("result")) {

                    for (Map.Entry<String, LocalDateTime> entry : fieldValues
                            .entrySet()) {
                        String fieldName = entry.getKey();
                        LocalDateTime targetValue = entry.getValue();
                        String fieldValue = packet.get("trx_time").asText();
                        //
                        LocalDateTime fechaJson = conversionFechaISO(fieldValue);

                        if (fechaDatabase.isBefore(fechaJson)) {
                            System.out.println(packet.toPrettyString());

                            // Convertir data de base64 a hexadecimal
                            String base64Data = packet.get("data").
                                    asText();
                            byte[] binaryData = Base64.getDecoder().decode(base64Data);
                            String hexData = bytesToHex(binaryData);
                            System.out.println("Data (Hexadecimal): " + hexData);

                            PreparedStatement preparedStatement = iotib.
                                    insertDataGateway(packet, hexData,
                                            "INSERT INTO data (DEV_EUI, "
                                            + "APP_EUI, CODE_RATE, "
                                            + "DATA_RATE, FREQUENCY,"
                                            + " PORT, RSSI_UP, SNR, GW_EUI,"
                                            + " TRX_TIME, PAYLOAD, COUNTER, DIRECTION) VALUES"
                                            + " (?,?,?,?,?,?,?,?,?,?,?,?, ?)"
                                            + "", connection);

                            String app_eui = iotib.obtenerXstring(connection,
                                    "APP_EUI", "data");

                            switch (app_eui) {
                                case "00-18-b2-53-42-50-4c-31":
                                    AdeunisPayload payload_adeunis = new AdeunisPayload(hexData, connection);
                                    payload_adeunis.FrameCode();
                                    break;

                                case "00-18-b2-44-52-49-43-31":
                                    payload_adeunis = new AdeunisPayload(hexData, connection);
                                    payload_adeunis.FrameCode();
                                    break;

                                case "24-e1-24-c0-00-2a-00-01":
                                    MilesightPayload payload_milesight = new MilesightPayload(hexData, connection);
                                    payload_milesight.FrameCode();
                                    break;

                                case "a8-40-41-00-00-00-01-01":
                                    DRAGINOPayload payload_DRAGINO = new DRAGINOPayload(hexData, connection);
                                    payload_DRAGINO.FrameCode();
                                    break;

                                case "46-4e-37-94-bd-2c-98-d1":
                                    SenseCapPayload payload_SenseCap = new SenseCapPayload(hexData, connection);
                                    payload_SenseCap.FrameCode();
                                    break;
                            }
                            //

                        } else {
                            acabado = true;
                        }
                    }

                    if (acabado == true) {
                        iotib.cerrarConexionSinDatos(connection);
                        break;
                    }

                }

            }
        } else {
            HttpURLConnection connection_http = (HttpURLConnection) new URL(
                    packetsUrl).openConnection();
            connection_http.setRequestMethod("GET");

            int responseCode = connection_http.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection_http.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }

                reader.close();

                String packets = response.toString();
                JsonNode jsonNode = objectMapper.readTree(packets);

                for (JsonNode packet : jsonNode.get("result")) {

                    System.out.println(packet.toPrettyString());

                    // Convertir data de base64 a hexadecimal
                    String base64Data = packet.get("data").
                            asText();
                    byte[] binaryData = Base64.getDecoder().decode(base64Data);
                    String hexData = bytesToHex(binaryData);
                    System.out.println("Data (Hexadecimal): " + hexData);

                    PreparedStatement preparedStatement = iotib.
                            insertDataGateway(packet, hexData,
                                    "INSERT INTO data (DEV_EUI, "
                                    + "APP_EUI, CODE_RATE, "
                                    + "DATA_RATE, FREQUENCY,"
                                    + " PORT, RSSI_UP, SNR, GW_EUI,"
                                    + " TRX_TIME, PAYLOAD, COUNTER, DIRECTION) VALUES"
                                    + " (?,?,?,?,?,?,?,?,?,?,?,?, ?)"
                                    + "", connection);

                    String app_eui = iotib.obtenerXstring(connection, "APP_EUI", "data");

                    switch (app_eui) {
                        case "00-18-b2-53-42-50-4c-31":
                            AdeunisPayload payload_adeunis = new AdeunisPayload(hexData, connection);
                            payload_adeunis.FrameCode();
                            break;

                        case "00-18-b2-44-52-49-43-31":
                            payload_adeunis = new AdeunisPayload(hexData, connection);
                            payload_adeunis.FrameCode();
                            break;

                        case "24-e1-24-c0-00-2a-00-01":
                            MilesightPayload payload_milesight = new MilesightPayload(hexData, connection);
                            payload_milesight.FrameCode();
                            break;

                        case "a8-40-41-00-00-00-01-01":
                            DRAGINOPayload payload_DRAGINO = new DRAGINOPayload(hexData, connection);
                            payload_DRAGINO.FrameCode();
                            break;

                        case "46-4e-37-94-bd-2c-98-d1":
                            SenseCapPayload payload_SenseCap = new SenseCapPayload(hexData, connection);
                            payload_SenseCap.FrameCode();
                            break;
                    }
                    //

                }
                iotib.cerrarConexionSinDatos(connection);
            }
        }

    }
}

////////////////////////////////////////////////////////////////////////////
/////////// MÉTODO NO UTILIZADO ///////////
/* Devuelve la fecha más aproxima a la actual del sistema de ejecución. Se le
       pasa por parámetros dos fechas en formato String e internamente utilizando otros
       métodos, pasa a formato LocalDateTime y se comparán. Se vuelve a converitr a
       String y es devuelto. 
    private String comparadorFechas(String trx_time_primero, String trx_time_ultimo) {
        LocalDateTime primeraFecha = conversionFechaNormal(trx_time_primero);
        LocalDateTime ultimaFecha = conversionFechaNormal(trx_time_ultimo);
        String fechaADevolver;

        if (primeraFecha.isAfter(ultimaFecha)) {
            fechaADevolver = trx_time_primero;
        } else {
            fechaADevolver = trx_time_ultimo;
        }

        return fechaADevolver;
    }
    //////////////////////////////////////////////////////////////////////////*/
/////////// MÉTODO NO UTILIZADO ///////////
/* Devuelve en unas String todos los paquetes presentes en el gateway. Primero
       realiza una petición "GET" al gateway y luego junta todos los paquetes en
       una variable StringBuilder para luego ser convertida a String.
    public String getPackets(String token) throws IOException {
        String packetsUrl = baseUrl + "/api/lora/packets?token=" + token;

        HttpURLConnection connection = (HttpURLConnection) new URL(packetsUrl)
                .openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }

            reader.close();

            return response.toString();
        } else {
            throw new IOException("Error al obtener paquetes con código de respu"
                    + "esta: " + responseCode);
        }
    }
    //////////////////////////////////////////////////////////////////////////*/
