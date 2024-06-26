package org.example.InternationalMathContest.manager;

import org.example.Main;
/*import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
*/

import java.io.IOException;
import java.io.InputStream;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RegistrationManager {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void registerUser() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Student Registration");
        
        String firstName = getUserInput(scanner, "Enter first name: ");
        String lastName = getUserInput(scanner, "Enter last name: ");
        String emailAddress = getValidEmail(scanner);
        int schoolRegNo = getIntInput(scanner, "Enter school registration number: ");
        LocalDate dateOfBirth = getValidDate(scanner);
        String imagePath = getUserInput(scanner, "Enter image path: ");
        String userName = getUserInput(scanner, "Enter username: ");
        String password = getUserInput(scanner, "Enter password: ");
        
        String applicantID = generateUniqueApplicantID(schoolRegNo);
        
        if (isValidSchoolRegNo(schoolRegNo, firstName, lastName, emailAddress)) {
            storeApplicantDetails(applicantID, firstName, lastName, emailAddress, schoolRegNo, 
                                  dateOfBirth, imagePath, userName, password);
            System.out.println("Registration successful. Please wait for confirmation from your school representative.");
        } else {
            System.out.println("Error: Invalid school registration number. Please try again.");
        }
    }

    private static String getUserInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            System.out.print(prompt);
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return value;
    }

    private static String getValidEmail(Scanner scanner) {
        String email;
        do {
            email = getUserInput(scanner, "Enter email address: ");
        } while (!isValidEmail(email));
        return email;
    }

    private static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private static LocalDate getValidDate(Scanner scanner) {
        LocalDate date = null;
        while (date == null) {
            String dateString = getUserInput(scanner, "Enter date of birth (YYYY-MM-DD): ");
            date = parseDate(dateString);
        }
        return date;
    }

    private static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return null;
        }
    }

    private static String generateUniqueApplicantID(int schoolRegNo) {
        String prefix = "APP" + schoolRegNo + "/";
        int sequenceNumber = 1;

        try (Connection conn = getDatabaseConnection()) {
            String query = "SELECT applicantID FROM Applicant WHERE schoolRegNo = ? ORDER BY applicantID DESC LIMIT 1";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, schoolRegNo);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String lastApplicantID = rs.getString("applicantID");
                        String[] parts = lastApplicantID.split("/");
                        if (parts.length > 1) {
                            sequenceNumber = Integer.parseInt(parts[1]) + 1;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error generating unique applicant ID: " + e.getMessage());
            return null;
        }

        return prefix + String.format("%04d", sequenceNumber);
    }

    private static boolean isValidSchoolRegNo(int schoolRegNo, String firstName, String lastName, String emailAddress) {
        try (Connection conn = getDatabaseConnection()) {
            String query = "SELECT sr.emailAddress FROM School s " +
                           "JOIN SchoolRepresentative sr ON s.schoolRepID = sr.schoolRepID " +
                           "WHERE s.schoolRegNo = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, schoolRegNo);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String repEmail = rs.getString("emailAddress");
                        System.out.println("Your application has been submitted and is pending approval.");
                        //sendConfirmationEmail(repEmail, firstName, lastName, emailAddress, schoolRegNo);
                        return true;
                    } else {
                        System.out.println("School is invalid. Please try again.");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    /*
    private static void sendConfirmationEmail(String repEmail, String firstName, String lastName, String emailAddress, int schoolRegNo) {
        Properties props = loadProperties("mail.properties");
        if (props == null) return;

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("mail.user"), props.getProperty("mail.password"));
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("mail.from")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(repEmail));
            message.setSubject("New Applicant Confirmation Required");
            message.setText("Dear School Representative,\n\n" +
                            "A new applicant requires your confirmation:\n\n" +
                            "Name: " + firstName + " " + lastName + "\n" +
                            "Email: " + emailAddress + "\n" +
                            "School Registration Number: " + schoolRegNo + "\n\n" +
                            "Please log in to the system to confirm this applicant.\n\n" +
                            "Best regards,\nMaths Challenge System");

            Transport.send(message);
            System.out.println("Confirmation email sent to school representative.");
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }*/

    private static void storeApplicantDetails(String applicantID, String firstName, String lastName,
                                              String emailAddress, int schoolRegNo, LocalDate dateOfBirth,
                                              String imagePath, String userName, String password) {
        String sql = "INSERT INTO Applicant (applicantID, firstName, lastName, emailAddress, schoolRegNo, " +
                     "dateOfBirth, imagePath, userName, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, applicantID);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, emailAddress);
            pstmt.setInt(5, schoolRegNo);
            pstmt.setDate(6, Date.valueOf(dateOfBirth));
            pstmt.setString(7, imagePath);
            pstmt.setString(8, userName);
            pstmt.setString(9, hashPassword(password));
    
            int affectedRows = pstmt.executeUpdate();
    
            if (affectedRows > 0) {
                System.out.println("Applicant details stored successfully.");
            } else {
                System.out.println("Failed to store applicant details.");
            }
    
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    
    private static String hashPassword(String password) {
        // TODO: Implement proper password hashing (e.g., using BCrypt)
        return "hashed_" + password;
    }

    private static Connection getDatabaseConnection() throws SQLException {
        Properties props = loadProperties("database.properties");
        if (props == null) {
            throw new SQLException("Unable to load database properties");
        }
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password"));
    }

    private static Properties loadProperties(String filename) {
        Properties props = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return null;
            }
            props.load(input);
            return props;
        } catch (IOException e) {
            System.out.println("Error loading " + filename + ": " + e.getMessage());
            return null;
        }
    }
}