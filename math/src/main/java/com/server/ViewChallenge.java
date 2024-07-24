package com.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

class ViewChallenge {

	static  String viewChallenges() throws SQLException {
		String sql = "SELECT challengeNo, challengeName, openDate, closeDate,attemptDuration,noOfQuestions FROM Challenge WHERE closeDate >= CURRENT_DATE";
		 List<Map<String, Object>> challengesList = new ArrayList<>();
		try(PreparedStatement stmt = Server.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery()){
					if(!rs.next()){
						return "No challenges available";
					}
					else{
						rs.beforeFirst(); // Move the cursor before the first row
						while (rs.next()) {
							Map<String, Object> challenge = new HashMap<>();

							 // Convert time to LocalTime and format it
							 Time attemptDuration = rs.getTime("attemptDuration");
							 LocalTime localTime = attemptDuration.toLocalTime();
							String formattedTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
							
							 // Put values into the map
							 challenge.put("challengeNo", rs.getInt("challengeNo"));
							 challenge.put("challengeName", rs.getString("challengeName"));
							 challenge.put("openDate", rs.getDate("openDate"));
							 challenge.put("closeDate", rs.getDate("closeDate"));
							 challenge.put("attemptDuration", formattedTime);
							 challenge.put("numberofQuestions", rs.getInt("noOfQuestions"));
					 
							 // Add the map to the list
							 challengesList.add(challenge);
					  }

					}
						 }
					// Debug statements to check the size and contents of challengesList
				System.out.println("Number of challenges fetched: " + challengesList.size());
				for (Map<String, Object> challenge : challengesList) {
					System.out.println("Challenge details: " + challenge);
				}
					
				 Gson gson = new Gson();
					return gson.toJson(challengesList);	
			}
		
	}
			
				
				
			


