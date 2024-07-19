package com.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class ViewApplicant {
	static Scanner scan = new Scanner(System.in);
	
	static void viewApplicants() throws UnknownHostException, IOException {
		while(true) {
			System.out.println("To view applicants registered under your school type \"viewApplicants\"");
			String input = scan.nextLine();
			
			if(input.equals("viewApplicants")) {
				String response = Register.sendDetails(input);
				printApplicants(response);
				confirmRejectApplicant();
				break; // Exit the loop if the correct input is provided
			}
			else {
				System.out.println("Invalid input. Please type \"viewApplicants\" to proceed.");
			}
		}
			
			
	}
	
	static void printApplicants(String jsonResponse) {
        Gson gson = new Gson();
        Type applicantListType = new TypeToken<List<Map<String, Object>>>() {}.getType();
        List<Map<String, Object>> applicants = gson.fromJson(jsonResponse, applicantListType);

        for (Map<String, Object> applicant : applicants) {
            System.out.println("Applicant details: " + applicant);
        }
    }
	static void confirmRejectApplicant() throws UnknownHostException, IOException {
		while(true) {
			System.out.println();
			System.out.println("To confirm an applicant type \"confirm yes username\"");
			System.out.println("To reject an applicant type \"confirm no username\"");
			String input  = scan.nextLine();
			String[] Details = input.split("\\s+");
			
			if(Details.length==3 && Details[1].equals("yes") && Details[0].equals("confirm")) {
				String response = Register.sendDetails(input);
				System.out.println(response);
				handleResponse(response);
				break; // Exit the loop if the correct input is provided
			}
			else if(Details.length==3 && Details[1].equals("no") && Details[0].equals("confirm")) {
				String response = Register.sendDetails(input);
				System.out.println(response);
				handleResponse(response);
				break; // Exit the loop if the correct input is provided
				
			}
			else {
				System.out.println("Invalid input.");
			}
		}
		
		
	}
	static boolean handleResponse(String response) throws UnknownHostException, IOException {
		if(response.endsWith("has been rejected") || response.endsWith("is now a participant")) {
			while(true) {
				System.out.println("To confirm or reject other applicants under your school "
						+ "type \"yes\"");
				System.out.println("To exist the application type \"done\"");
				String input = scan.nextLine();
				switch (input.toLowerCase()) {
					case "yes":
					confirmRejectApplicant();
						break;

					case "done":
						System.out.println("logout successful");
						return true; // Signal to exit completely
					default:
					System.out.println("Invalid input");
				}
			}
		}
		else{
			confirmRejectApplicant();
		}
		return false; // Continue interaction
	}

}
					
	
