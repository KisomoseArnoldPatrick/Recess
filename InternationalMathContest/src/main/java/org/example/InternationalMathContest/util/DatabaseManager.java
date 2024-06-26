package org.example.InternationalMathContest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseManager {
    private static final String PROPERTIES_FILE = "/database.properties";
    private static Properties props = new Properties();

    static {
        try (InputStream is = DatabaseManager.class.getResourceAsStream(PROPERTIES_FILE)) {
            props.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.username"),
            props.getProperty("db.password")
        );
    }
}