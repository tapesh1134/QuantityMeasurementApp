package org.quantitymeasurement.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5433/quantitymeasurement";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0000";

    private DatabaseConnection() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static synchronized DatabaseConnection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DatabaseConnection();
            }
            return instance;
        } catch (SQLException e) {
            throw new RuntimeException("Database Connection Failed", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}