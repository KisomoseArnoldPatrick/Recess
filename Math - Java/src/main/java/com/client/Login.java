package com.client;

import java.io.IOException;
import java.net.UnknownHostException;

class Login {
	static void login(String input) throws UnknownHostException, IOException {
		String[] Details = input.split("\\s+");
		if(Details.length == 3) {
			String response = Register.sendDetails(input);
			handleResponse(response);// Call handleResponse() after receiving the response
	}
		else {
			System.out.println("Invalid login details. Please provide username and password.");
			Client.start();
		}
	}
	
	static void handleResponse(String response) throws UnknownHostException, IOException {
		if(response.contains("representative")) {
			int lastSpaceIndex = response.lastIndexOf(' ');
					if (lastSpaceIndex != -1) {
						String messagePart = response.substring(0, lastSpaceIndex);
						String numberPart = response.substring(lastSpaceIndex + 1);
						int schRegNo = Integer.parseInt(numberPart);
						System.out.println(messagePart);
						ViewApplicant.viewApplicants(schRegNo);
					}
		}

		else if(response.contains("participant")) {
			int lastSpaceIndex2 = response.lastIndexOf(' ');
					if (lastSpaceIndex2 != -1) {
						String messagePart = response.substring(0, lastSpaceIndex2);
						String numberPart = response.substring(lastSpaceIndex2 + 1);
						int participantID = Integer.parseInt(numberPart);
						System.out.println(messagePart);
						ViewChallenge.viewChallenges(participantID);
					}
		}
		else {
			System.out.println(response);
			retryLogin();

		}
	
	}

	
           
                    
               

	static void retryLogin() throws UnknownHostException, IOException {
        Client.start(); // Call Client.start() to prompt the user again
    }
		

}
