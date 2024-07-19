package com.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class ViewChallenge {
	static Scanner scan = new Scanner(System.in);
	
	static void viewChallenges() throws UnknownHostException, IOException {
			while(true) {
				System.out.println("To see valid challenges type \"viewChallenges\"");
				String input = scan.nextLine();
				
				if (input.equals("viewChallenges")) {
					String response = Register.sendDetails(input);
					printChallenges(response);
					attemptChallenge();
					break; // Exit the loop if the correct input is provided
				} else {
					System.out.println("Invalid input. Please type \"viewChallenges\" to proceed.");
				}
			}

		
	}
	
	static void printChallenges(String jsonResponse) {
		 Gson gson = new Gson();
	        Type challengeListType = new TypeToken<List<Map<String, Object>>>() {}.getType();
	        List<Map<String, Object>> challenges = gson.fromJson(jsonResponse, challengeListType);

	        for (Map<String, Object> challenge : challenges) {
	            System.out.println("Challenge details: " + challenge);
	        }
	}
	static void attemptChallenge() throws UnknownHostException, IOException {
		while(true) { 
			System.out.println();
			System.out.println("To attempt a challenge type \"attemptChallenge challengeNumber\"");
			String input  = scan.nextLine();
			String[] Details = input.split("\\s+");
			
			if(Details.length==2 && Register.isInteger(Details[1])) {
				String response = Register.sendDetails(input);
				System.out.println(response);
				break; // Exit the loop if the correct input is provided
			}
			else {
				System.out.println("Invalid input. Please type \"attemptChallenge challengeNumber\" to proceed.");
			}
		}
		
	}

}

