/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;


/**
 *
 * @Author Fgabriel
 * Singleton Connection
 */
public class DBConnection {
    public  static Connection getConnection() throws Exception {
        return MysqlConnection.getInstance().getConnection();
    }
}
