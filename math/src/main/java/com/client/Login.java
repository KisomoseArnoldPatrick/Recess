package com.client;

import java.io.IOException;
import java.net.UnknownHostException;

class Login {
	static void login(String input) throws UnknownHostException, IOException {
		String[] Details = input.split("\\s+");
		if(Details.length == 3) {
			String response = Register.sendDetails(input);
			System.out.println(response);
			handleResponse(response);// Call handleResponse after receiving the response
			
		
	}
		else {
			System.out.println("Invalid login details. Please provide username and password.");
			Client.start();
		}
	}
	
	static void handleResponse(String response) throws UnknownHostException, IOException {
            switch (response) {
                case "Successful login as school representative":
                    ViewApplicant.viewApplicants();
                    break;
                case "Successful login as participant":
                    ViewChallenge.viewChallenges();
                    break;
                default:
                    //Wrong password or username
                    Client.start();//Call Client.start() to prompt the user again
                    break;
            }
	}
		

}
