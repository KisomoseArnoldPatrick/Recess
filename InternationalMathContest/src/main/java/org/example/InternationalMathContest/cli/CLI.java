package org.example.InternationalMathContest.cli;

import org.example.InternationalMathContest.manager.RegistrationManager;
import org.example.InternationalMathContest.manager.LoginManager;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner;
    private final RegistrationManager registrationManager;
    private final LoginManager loginManager;

    public CLI() {
        this.scanner = new Scanner(System.in);
        this.registrationManager = new RegistrationManager();
        this.loginManager = new LoginManager(this.scanner);
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            running = processChoice(choice);
        }
        closeScanner();
        System.out.println("Thank you for using the Maths Challenge System.");
    }

    private void displayMenu() {
        System.out.println("\nWelcome to the International Maths Challenge System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    private int getUserChoice() {
        while (true) {
            System.out.print("Choose an option: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= 3) {
                    return choice;
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private boolean processChoice(int choice) {
        switch (choice) {
            case 1:
                register();
                return true;
            case 2:
                login();
                return true;
            case 3:
                System.out.println("Goodbye!");
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
                return true;
        }
    }

    private void register() {
        System.out.println("Proceeding to registration...");
        try {
            registrationManager.registerUser();
        } catch (Exception e) {
            System.out.println("An error occurred during registration: " + e.getMessage());
        }
    }

    private void login() {
        System.out.println("Proceeding to login...");
        try {
            loginManager.login();
        } catch (Exception e) {
            System.out.println("An error occurred during registration: " + e.getMessage());
        }
    }

    private void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}