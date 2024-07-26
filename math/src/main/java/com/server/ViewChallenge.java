package com.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

class ViewChallenge {

    
	static  String viewChallenges() throws SQLException {
		String sql = "SELECT challengeNo, challengeName, openDate, closeDate FROM Challenge";
		 List<Map<String, Object>> challengesList = new ArrayList<>();
		try(PreparedStatement stmt = Server.conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			
			  while (rs.next()) {
		            Map<String, Object> challenge = new HashMap<>();
		            challenge.put("challengeNo", rs.getInt("challengeNo"));
		            challenge.put("challengeName", rs.getString("challengeName"));
		            challenge.put("openDate", rs.getDate("openDate"));
		            challenge.put("closeDate", rs.getDate("closeDate"));
		            challengesList.add(challenge);
			  }
				
		}
		// Debug statements to check the size and contents of challengesList
	    System.out.println("Number of challenges fetched: " + challengesList.size());
	    for (Map<String, Object> challenge : challengesList) {
	        System.out.println("Applicant details: " + challenge);
	    }
			
		
		 Gson gson = new Gson();
		    return gson.toJson(challengesList);
		
	}

}
