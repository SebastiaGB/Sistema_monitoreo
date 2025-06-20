package Iotib.decoder;

public class Decoder {

    public static void main(String[] args) throws Exception {

        String baseUrl = "https://192.168.2.1";
        // String baseUrl = scanner.nextLine();
        Gateway_prueba conexionAPI = new Gateway_prueba(baseUrl);

        String token = conexionAPI.obtainToken("admin", "admin");

        System.out.println("Token obtenido: " + token);

        conexionAPI.paquetesPorCampos(token);   
    }

}
