package com.ies.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        scanner = new Scanner(System.in);
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        scanner.close();
    }

    public void run() {
        try {
            while (true) {
                System.out.println("Welcome to the International Education System Mathematics Competition System");
                System.out.println("Enter function to continue or enter function>>help to view details");
                System.out.print(">>> ");

                String command = scanner.nextLine().trim();
                if (command.equalsIgnoreCase("exit")) {
                    break;
                }
                processCommand(command);
            }
        } catch (IOException e) {
            System.out.println("Error in client: " + e.getMessage());
        } finally {
            try {
                stopConnection();
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    private void processCommand(String command) throws IOException {
        String[] parts = command.split(" ");
        String action = parts[0].toLowerCase();

        switch (action) {
            case "register":
                if (parts.length != 8) {
                    System.out.println("Invalid registration format. Use: Register username firstname lastname emailAddress date_of_birth school_registration_number image_file.png");
                } else {
                    register(parts);
                }
                break;
            case "viewchallenges":
                viewChallenges();
                break;
            case "attemptchallenge":
                if (parts.length != 2) {
                    System.out.println("Invalid format. Use: attemptChallenge challengeNumber");
                } else {
                    attemptChallenge(parts[1]);
                }
                break;
            case "viewapplicants":
                viewApplicants();
                break;
            case "confirm":
                if (parts.length != 3 || (!parts[1].equals("yes") && !parts[1].equals("no"))) {
                    System.out.println("Invalid format. Use: confirm yes/no username");
                } else {
                    confirmApplicant(parts[1], parts[2]);
                }
                break;
            case "function>>help":
                displayHelp();
                break;
            default:
                System.out.println("Invalid choice. Enter one of the functions or enter function>>help for help.");
        }
    }

    private void register(String[] args) throws IOException {
        String message = String.join(" ", args);
        String response = sendMessage("REGISTER " + message);
        System.out.println(response);
    }

    private void viewChallenges() throws IOException {
        String response = sendMessage("VIEW_CHALLENGES");
        if (response.startsWith("No challenges") || response.startsWith("Error")) {
            System.out.println(response);
        } else {
            System.out.println("Available Challenges:");
            System.out.println(response);
        }
    }

    private void attemptChallenge(String challengeNumber) throws IOException {
        String response = sendMessage("ATTEMPT_CHALLENGE " + challengeNumber);
        System.out.println(response);
        // Here you would implement the logic to handle the challenge attempt
        // This might involve a series of message exchanges with the server
    }

    private void viewApplicants() throws IOException {
        String response = sendMessage("VIEW_APPLICANTS");
        System.out.println(response);
    }

    private void confirmApplicant(String decision, String username) throws IOException {
        String response = sendMessage("CONFIRM_APPLICANT " + decision + " " + username);
        System.out.println(response);
    }

    private void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("1. Register: Register username firstname lastname emailAddress date_of_birth school_registration_number image_file.png");
        System.out.println("2. View Challenges: ViewChallenges");
        System.out.println("3. Attempt Challenge: attemptChallenge challengeNumber");
        System.out.println("4. View Applicants (for school representatives): viewApplicants");
        System.out.println("5. Confirm Applicant (for school representatives): confirm yes/no username");
        System.out.println("6. Exit: exit");
    }

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.startConnection("localhost", 5000); // Adjust IP and port as needed
            client.run();
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}