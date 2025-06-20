package com.mycompany.paquetesperdidos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class database {

    //Variables privadas 
    private String URL;
    private String username;
    private String password;

    // Constructor de database
    public database(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }

    //Método para establecer conexión con la base de datos
    public Connection abrirConexion() throws SQLException {
        String URL_COMPLETA = URL + "?username=" + username + "&password=" + password;
        Connection connection = DriverManager.getConnection(URL_COMPLETA, username, password);

        return connection;
    }

    /* Método para cerrar la conexión en caso de haber alguna sentencia SQL preparada */
    public void cerrarConexionConDatos(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
        connection.close();
    }

    /* Método para cerrar la conexión en caso de NO haber alguna sentencia SQL preparada */
    public void cerrarConexionSinDatos(Connection connection) throws SQLException {
        connection.close();
    }

}
