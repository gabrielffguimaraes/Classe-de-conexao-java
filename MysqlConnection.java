/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gl-ki
 */
public class MysqlConnection {
    private final String URL = "jdbc:mysql://localhost:3306/teste";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private static Connection connection;
    
    // PRIVATE METHODS
    // GARANTE QUE NINGUÉM IRÁ INSTANCIAR ESTA CLASSE
    private MysqlConnection()  {}
    
    private static class InstanceHolder {
        private final static MysqlConnection instance = new MysqlConnection();
        private InstanceHolder() {
            this.instance.setConnection();
        }
    }
    private void setConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
    }
    private boolean isValidConnection() {
        if(connection == null) {
            return false;
        }
        try {
            connection.getMetaData();
        } catch(SQLException ex) {
            return false;
        }
        return true;
    }
    
    // PUBLIC METHODS
    public static MysqlConnection getInstance() {
        return InstanceHolder.instance;
    }

    public Connection getConnection() throws Exception {
        if(!isValidConnection()) {
            this.setConnection();
        }
        if(connection == null) {
            throw new RuntimeException("Erro ao estabelecer conexão.");
        }
        return connection;
    } 
}
