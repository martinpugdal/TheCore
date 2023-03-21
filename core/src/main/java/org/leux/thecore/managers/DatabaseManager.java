package org.leux.thecore.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://172.18.0.1:3306/test", "root", "GGhrd2iv:_2e&v");
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
