package org.example.InternationalMathContest.manager;

import org.example.InternationalMathContest.util.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginManager {
    private final Scanner scanner;

    public LoginManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean login() {
        System.out.println("Login Process");
        System.out.print("Enter your Participant ID: ");
        String participantID = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        return authenticateUser(participantID, password);
    }

    private boolean authenticateUser(String participantID, String password) {
        String sql = "SELECT * FROM Participant WHERE participantID = ? AND password = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, participantID);
            pstmt.setString(2, password); // Note: In a real application, passwords should be hashed

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Login successful. Welcome, " + rs.getString("firstName") + "!");
                    return true;
                } else {
                    System.out.println("Login failed. Invalid Participant ID or password.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during login: " + e.getMessage());
            return false;
        }
    }
}