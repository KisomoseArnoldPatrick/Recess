package com.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class ViewApplicant {
	static void viewApplicants(int schRegNo) throws UnknownHostException, IOException {
		while(true) {
			System.out.println("To view applicants registered under your school type \"viewApplicants\"");
			String input = Client.scan.nextLine();
			String details = input+" "+schRegNo;
			
			if(input.equals("viewApplicants")) {
				String response = Register.sendDetails(details);
				if(response.equals("No applicants available")) {
					System.out.println(response);
					break;
				}else{
					List<Map<String, Object>> applicants = printApplicants(response);
					int applicantCount = applicants.size();
                    if(applicantCount == 1) {
                        confirmRejectSingleApplicant();
                    } else {
                        confirmRejectMultipleApplicants(applicantCount);
                    }
				break; // Exit the loop if the correct input is provided

				}
				
			}
			else {
				System.out.println("Invalid input. Please type \"viewApplicants\" to proceed.");
			}
		}
				
	}
	
	static List<Map<String, Object>> printApplicants(String jsonResponse) {
        Gson gson = new Gson();
        Type applicantListType = new TypeToken<List<Map<String, Object>>>() {}.getType();
        List<Map<String, Object>> applicants = gson.fromJson(jsonResponse, applicantListType);

        for (Map<String, Object> applicant : applicants) {
            System.out.println("Applicant details: " + applicant);
        }
		return applicants;
    }

	static void confirmRejectSingleApplicant() throws UnknownHostException, IOException {
		while(true) {
			System.out.println();
			System.out.println("To confirm an applicant type \"confirm yes username\"");
			System.out.println("To reject an applicant type \"confirm no username\"");
			String input  = Client.scan.nextLine();
			String[] Details = input.split("\\s+");
			
			if(Details.length==3 && Details[1].equals("yes") && Details[0].equals("confirm")) {
				String response = Register.sendDetails(input);
				System.out.println(response);
				break; // Exit the loop if the correct input is provided
			}
			else if(Details.length==3 && Details[1].equals("no") && Details[0].equals("confirm")) {
				String response = Register.sendDetails(input);
				System.out.println(response);
				break; // Exit the loop if the correct input is provided
				
			}
			else {
				System.out.println("Invalid input.");
			}
		}
		
		
	}

	static void confirmRejectMultipleApplicants(int applicantCount) throws UnknownHostException, IOException {
        while (applicantCount > 0) {
            System.out.println();
            System.out.println("To confirm an applicant type \"confirm yes username\"");
            System.out.println("To reject an applicant type \"confirm no username\"");
            String input = Client.scan.nextLine();
            String[] Details = input.split("\\s+");

            if (Details.length == 3 && Details[1].equals("yes") && Details[0].equals("confirm")) {
                String response = Register.sendDetails(input);
                System.out.println(response);
                applicantCount--;
                if (applicantCount > 0) {
                    System.out.println("Applicants remaining: " + applicantCount);
                }
                handleResponse(response, applicantCount);
            } else if (Details.length == 3 && Details[1].equals("no") && Details[0].equals("confirm")) {
                String response = Register.sendDetails(input);
                System.out.println(response);
                applicantCount--;
                if (applicantCount > 0) {
                    System.out.println("Applicants remaining: " + applicantCount);
                }
                handleResponse(response, applicantCount);
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

	static boolean handleResponse(String response, int applicantCount) throws UnknownHostException, IOException {
        if (response.endsWith("has been rejected") || response.endsWith("is now a participant")) {
            if (applicantCount == 0) {
                System.out.println("All applicants have been processed. Exiting the application.");
                return true; // Signal to exit completely
            } else {
                while (true) {
                    System.out.println("To confirm or reject other applicants under your school type \"yes\"");
                    System.out.println("To exit the application type \"logout\"");
                    String input = Client.scan.nextLine();
                    switch (input.toLowerCase()) {
                        case "yes":
                            return false; // Continue interaction

                        case "logout":
                            System.out.println("Logout successful");
                            return true; // Signal to exit completely
                        default:
                            System.out.println("Invalid input");
                    }
                }
            }
        } else {
            if (applicantCount > 0) {
                confirmRejectMultipleApplicants(applicantCount);
            }
        }
        return false; // Continue interaction
    }
}


		
	
