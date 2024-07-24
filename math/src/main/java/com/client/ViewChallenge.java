package com.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class ViewChallenge {
	
	static void viewChallenges(int participantID) throws UnknownHostException, IOException {
			while(true) {
				System.out.println("To see valid challenges type \"viewChallenges\"");
				String input = Client.scan.nextLine();
				
				if (input.equals("viewChallenges")) {
					String response = Register.sendDetails(input);
					handleResponse(response, participantID);
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
	
	static  void handleResponse(String response, int participantID) throws UnknownHostException, IOException{
		switch(response){
			case "No challenges available":
				System.out.println("No challenges available");
				break;

			default:
				printChallenges(response);
				AttemptChallenge attemptChallenge = new AttemptChallenge();
				attemptChallenge.attemptChallenge(participantID);
				break;
		}
	}

}

