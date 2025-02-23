/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;

/**
 *
 * @author judit
 */
public class DBConnection {

    private Connection connection;

    public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemamedico", "root", "admin"); //nombre de la base de datos al final del primer string, el usuario y la contrasenia que le puse
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }

    }

    public void desconectar() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
