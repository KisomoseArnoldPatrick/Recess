package com.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;

class Login {

    // checkLoginDetails() handles the login part
	static String[] checkLoginDetails(String userName, String password) throws SQLException {
		String sql = "SELECT password, participantID FROM Participant WHERE userName = ?";
		try(PreparedStatement stmt = Server.conn.prepareStatement(sql)){
			stmt.setString(1, userName);
			try(ResultSet rs = stmt.executeQuery()){
				if(!rs.next()) {
					 // ResultSet is empty, check in SchoolRepresentative table
					String sql2 = "SELECT password, schoolRegNo FROM School WHERE userName = ?";
					try(PreparedStatement stmt2 = Server.conn.prepareStatement(sql2)){
						stmt2.setString(1, userName);
						try(ResultSet rs2 = stmt2.executeQuery()){
							if(!rs2.next()) {
								return new String[] {"Wrong password or username", null};
							}
							else {
				                    // Verify the password
				                String storedHash = rs2.getString("password");//Retrieve the Stored Hashed Password
				                //verify the provided plaintext password against the stored hash
				                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedHash);
				                if (result.verified) {
									int schRegNo = rs2.getInt("schoolRegNo");
				                    return new String[] {"Successful login as school representative", String.valueOf(schRegNo)};
				                } else {
				                    return new String[] {"Wrong password or username", null};
				                }
				                } 
							}
						}
					}
				
				else {
				      // Verify the password
					  String storedHash = rs.getString("password");
					  BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedHash);
					  if (result.verified) {
						int participantID = rs.getInt("participantID");
						  return new String[] {"Successful login as participant", String.valueOf(participantID)};
					  } else {
						  return new String[] {"Wrong password or username", null};
					  }
	                } 
			}
		}
	}

}
