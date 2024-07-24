package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AttemptChallenge {
     PrintWriter out;
     Socket soc;
     BufferedReader in;
     Scanner scanner;

    String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    void attemptChallenge(int participantID) throws IOException {
        try {
            soc = new Socket("localhost",1060);
            out = new PrintWriter(soc.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            scanner = new Scanner(System.in);
            
            while(true) { 
                System.out.println();
                System.out.println("To attempt a challenge type \"attemptChallenge challengeNumber\"");
                String input  = scanner.nextLine();
                String[] Details = input.split("\\s+");
                String details = input+" "+participantID;
                
                if(Details.length==2 && Register.isInteger(Details[1]) && Details[0].equals("attemptChallenge")) {
                    String response = sendMessage(details);
                    System.out.println(response);
                    String startPrompt = in.readLine();
                    System.out.println(startPrompt);
    
                    System.out.println("Press Enter to start the challenge...");
                    scanner.nextLine(); // Wait for user to press Enter
                    out.println("start");
                    out.flush();
    
                    while (true) {
                        String line = in.readLine();
                        if (line == null || line.equals("END_OF_CHALLENGE")) {
                            break;
                        }
                        System.out.println(line);
            
                        if (line.startsWith("Enter your answer")) {
                            System.out.print("Your answer: ");
                            String answer = scanner.nextLine();
                            out.println(answer);
                            out.flush();
                        }
            
                        if (line.equals("Time's up!")) {
                            System.out.println("Challenge ended due to time limit.");
                            break;
                        }
            }
    
            String finalResult = in.readLine();
            System.out.println(finalResult);
                    break; // Exit the loop if the correct input is provided
                }
                else {
                    System.out.println("Invalid input. Please type \"attemptChallenge challengeNumber\" to proceed.");
                }
            }

            
        } catch (IOException e) {
            System.err.println("Error during challenge attempt: " + e.getMessage());
            e.printStackTrace();
        }finally {
            // Close resources in the finally block to ensure they are closed even if an exception occurs
            try {
                if (scanner != null) scanner.close();
                if (in != null) in.close();
                if (out != null) out.close();
                if (soc != null && !soc.isClosed()) soc.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

}
