package com.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

class ViewApplicant {

    static String viewApplicants() throws SQLException {
		String sql = "SELECT schoolRegNo,emailAddress,userName,firstName,lastName,dateOfBirth FROM Applicant";
		 List<Map<String, Object>> applicantsList = new ArrayList<>();
		try(PreparedStatement stmt = Server.conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			
			  while (rs.next()) {
		            Map<String, Object> applicant = new HashMap<>();
		            applicant.put("schoolRegNo", rs.getString("schoolRegNo"));
		            applicant.put("emailAddress", rs.getString("emailAddress"));
		            applicant.put("userName", rs.getString("userName"));
		            applicant.put("firstName", rs.getString("firstName"));
		            applicant.put("lastName", rs.getString("lastName"));
		            applicant.put("dateOfBirth", rs.getDate("dateOfBirth"));
		            applicantsList.add(applicant);
			  }
				
		}

        // Debug statements to check the size and contents of applicantsList
	    System.out.println("Number of applicants fetched: " + applicantsList.size());
	    for (Map<String, Object> applicant : applicantsList) {
	        System.out.println("Applicant details: " + applicant);
	    }
			
		 Gson gson = new Gson();
		    return gson.toJson(applicantsList);
	}

    	
	
	//confirm yes username
	static String confirmYes(String userName) throws SQLException {
		String sql = "SELECT * FROM Applicant WHERE userName =?";
		try(PreparedStatement stmt = Server.conn.prepareStatement(sql)){
			stmt.setString(1, userName);
			try(ResultSet rs = stmt.executeQuery()){
				if(!rs.next()) {
					return "Wrong username";
				}
				else {
					String sql2 = "INSERT INTO Participant(applicantID,firstName,lastName,emailAddress,"
							+ "dateOfBirth,image,password,schoolRegNo,userName) SELECT applicantID,firstName,"
							+ "lastName,emailAddress,dateOfBirth,image,password,schoolRegNo,"
							+ "userName FROM Applicant WHERE UserName = ?";
					try(PreparedStatement stmt2 = Server.conn.prepareStatement(sql2)){
						stmt2.setString(1, userName);
						stmt2.executeUpdate();
						
					}
					
					String sql3 = "DELETE FROM Applicant where userName = ?";
					try(PreparedStatement stmt3 = Server.conn.prepareStatement(sql3)){
						stmt3.setString(1, userName);
						stmt3.executeUpdate();
						
					}

					String firstName = rs.getString("firstName");
					String lastName = rs.getString("lastName");
					String applicantEmail = rs.getString("emailAddress");
					Email.notifyAcceptedApplicant(applicantEmail,firstName, lastName);
					return userName +" is now a participant";
				}
			}
			
		}
		
	}
	//confirm no username
	static String confirmNo(String userName) throws SQLException {
		String sql = "SELECT * FROM Applicant WHERE userName =?";
		try(PreparedStatement stmt = Server.conn.prepareStatement(sql)){
			stmt.setString(1, userName);
			try(ResultSet rs = stmt.executeQuery()){
				if(!rs.next()) {
					return "Wrong username";
				}
				else {
					String sql2 = "INSERT INTO Rejected(applicantID,firstName,lastName,emailAddress,"
							+ "dateOfBirth,image,password,schoolRegNo,userName) SELECT applicantID,firstName,"
							+ "lastName,emailAddress,dateOfBirth,image,password,schoolRegNo,"
							+ "userName FROM Applicant WHERE UserName = ?";
					try(PreparedStatement stmt2 = Server.conn.prepareStatement(sql2)){
						stmt2.setString(1, userName);
						stmt2.executeUpdate();
						
					}
					
					String sql3 = "DELETE FROM Applicant where userName = ?";
					try(PreparedStatement stmt3 = Server.conn.prepareStatement(sql3)){
						stmt3.setString(1, userName);
						stmt3.executeUpdate();
						
					}

					String firstName = rs.getString("firstName");
					String lastName = rs.getString("lastName");
					String applicantEmail = rs.getString("emailAddress");
					Email.notifyRejectedApplicant(firstName, lastName,applicantEmail);
					return userName +" has been rejected";
				}
			}
			
		}
		
	}
    
}
