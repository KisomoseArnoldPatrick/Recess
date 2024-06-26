package org.example.InternationalMathContest;

import java.sql.Connection;
import java.sql.Statement;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import org.example.InternationalMathContest.cli.CLI;
import org.example.InternationalMathContest.util.DatabaseManager;

public class MathContest {
    public static void main(String[] args) {
        try {
            initializeDatabase();
            CLI cli = new CLI();
            cli.start();
        } catch (Exception e) {
            System.err.println("Error starting the application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initializeDatabase() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             InputStream is = MathContest.class.getResourceAsStream("/db/schema.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                throw new IOException("Could not find schema.sql file");
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String sql = sb.toString();

            // Execute SQL statements
            stmt.execute(sql);
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}