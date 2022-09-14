package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/crud-scd", "crud-scd", "crud-scd");
        }
        return connection;
    }

    public static synchronized void closeConnection() throws SQLException {
        connection.close();
    }
}
