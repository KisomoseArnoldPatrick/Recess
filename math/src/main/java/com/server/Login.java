package com.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;

class Login {

    // checkDetails() handles the login part
	static String checkDetails(String userName, String password) throws SQLException {
		String sql = "SELECT password FROM Participant WHERE userName = ?";
		try(PreparedStatement stmt = Server.conn.prepareStatement(sql)){
			stmt.setString(1, userName);
			try(ResultSet rs = stmt.executeQuery()){
				if(!rs.next()) {
					 // ResultSet is empty, check in SchoolRepresentative table
					String sql2 = "SELECT password FROM SchoolRepresentative WHERE userName = ?";
					try(PreparedStatement stmt2 = Server.conn.prepareStatement(sql2)){
						stmt2.setString(1, userName);
						try(ResultSet rs2 = stmt2.executeQuery()){
							if(!rs2.next()) {
								return "Wrong password or username";
							}
							else {
				                    // Verify the password
				                String storedHash = rs2.getString("password");//Retrieve the Stored Hashed Password
				                //verify the provided plaintext password against the stored hash
				                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedHash);
				                if (result.verified) {
				                    return "Successful login as school representative";
				                } else {
				                    return "Wrong password or username";
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
						  return "Successful login as participant";
					  } else {
						  return "Wrong password or username";
					  }
	                } 
			}
		}
	}

}
