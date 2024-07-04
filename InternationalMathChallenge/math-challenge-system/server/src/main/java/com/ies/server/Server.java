package com.ies.server;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private Connection dbConnection;

    public Server(int port) throws IOException, SQLException {
        serverSocket = new ServerSocket(port);
        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathcompetition", "root", "root");
    }

    public void start() {
        while (true) {
            try {
                new ClientHandler(serverSocket.accept(), dbConnection).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private Connection dbConnection;

        public ClientHandler(Socket socket, Connection dbConnection) {
            this.clientSocket = socket;
            this.dbConnection = dbConnection;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String response = processRequest(inputLine);
                    out.println(response);
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String processRequest(String request) {
            String[] parts = request.split(" ");
            String action = parts[0].toUpperCase();

            switch (action) {
                case "REGISTER":
                    return registerUser(parts);
                case "VIEW_CHALLENGES":
                    return viewChallenges();
                case "ATTEMPT_CHALLENGE":
                    return attemptChallenge(parts[1]);
                case "VIEW_APPLICANTS":
                    return viewApplicants();
                case "CONFIRM_APPLICANT":
                    return confirmApplicant(parts[1], parts[2]);
                default:
                    return "Invalid request";
            }
        }

        private String registerUser(String[] parts) {
            if (parts.length != 9) {
                return "Invalid registration format";
            }

            String username = parts[2];
            String firstName = parts[3];
            String lastName = parts[4];
            String email = parts[5];
            String dateOfBirth = parts[6];
            int schoolRegNo;
            try {
                schoolRegNo = Integer.parseInt(parts[7]);
            } catch (NumberFormatException e) {
                return "Invalid school registration number";
            }
            String imagePath = parts[8];

            // Generate a random password (in practice, you'd want a more secure method)
            String password = generateRandomPassword();

            String sql = "INSERT INTO Applicant (schoolRegNo, emailAddress, userName, imagePath, firstName, lastName, password, dateOfBirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
                pstmt.setInt(1, schoolRegNo);
                pstmt.setString(2, email);
                pstmt.setString(3, username);
                pstmt.setString(4, imagePath);
                pstmt.setString(5, firstName);
                pstmt.setString(6, lastName);
                pstmt.setString(7, password);

                // Parse the date string to a java.sql.Date object
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date utilDate = sdf.parse(dateOfBirth);
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    pstmt.setDate(8, sqlDate);
                } catch (ParseException e) {
                    return "Invalid date format. Please use YYYY-MM-DD";
                }

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    return "User registered successfully. Your password is: " + password;
                } else {
                    return "Failed to register user";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error registering user: " + e.getMessage();
            }
        }

        private String generateRandomPassword() {
            // Implement a secure random password generation method
            return "tempPassword123";
        }

        private String viewChallenges() {
            String sql = "SELECT * FROM Challenge ORDER BY challengeNo";
            StringBuilder result = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            // Table header
            result.append(String.format("%-5s | %-20s | %-15s | %-15s | %-12s | %-10s | %-10s\n",
                    "No.", "Challenge Name", "Duration", "Questions", "Overall Mark", "Open Date", "Close Date"));
            result.append("-".repeat(100)).append("\n");

            try (PreparedStatement pstmt = dbConnection.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                boolean hasResults = false;
                while (rs.next()) {
                    hasResults = true;
                    int challengeNo = rs.getInt("challengeNo");
                    String challengeName = rs.getString("challengeName");
                    Time attemptDuration = rs.getTime("attemptDuration");
                    int noOfQuestions = rs.getInt("noOfQuestions");
                    int overallMark = rs.getInt("overallMark");
                    Date openDate = rs.getDate("openDate");
                    Date closeDate = rs.getDate("closeDate");

                    result.append(String.format("%-5d | %-20s | %-15s | %-15d | %-12d | %-10s | %-10s\n",
                            challengeNo,
                            challengeName,
                            attemptDuration.toString(),
                            noOfQuestions,
                            overallMark,
                            dateFormat.format(openDate),
                            dateFormat.format(closeDate)));
                }

                if (!hasResults) {
                    return "No challenges found in the database.";
                }

                return result.toString();

            } catch (SQLException e) {
                e.printStackTrace();
                return "Error retrieving challenges: " + e.getMessage();
            }
        }

        private String attemptChallenge(String challengeNumber) {
            // Implement logic to handle a challenge attempt
            return "Challenge " + challengeNumber + " attempted";
        }

        private String viewApplicants() {
            // Implement logic to retrieve and return applicants from the database
            return "List of applicants: Applicant 1, Applicant 2, Applicant 3";
        }

        private String confirmApplicant(String decision, String username) {
            // Implement logic to confirm or reject an applicant
            return "Applicant " + username + " " + (decision.equals("yes") ? "confirmed" : "rejected");
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(5000);
            System.out.println("Server started on port 5000");
            server.start();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}