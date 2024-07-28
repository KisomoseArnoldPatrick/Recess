package com.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import at.favre.lib.crypto.bcrypt.BCrypt;

class Register {
	
	//registerPupil() handles the registration part
	static String registerPupil(String registrationDetails) throws SQLException, IOException {
		String[] Details = registrationDetails.split("\\s+");
		String userName =Details[1];
		String firstName =Details[2];
		String lastName =Details[3];
		String emailAddress =Details[4];
		String dateOfBirth =Details[5];
		int schRegNo = Integer.parseInt(Details[6]);
		String password = Details[7];
		String imagePath =Details[8];
		String sql = "SELECT schoolRegNo, repName, emailAddress,schoolName FROM School WHERE schoolRegNo = ? ";
		try(PreparedStatement stmt = Server.conn.prepareStatement(sql)){
			stmt.setInt(1,schRegNo);
			try(ResultSet rs = stmt.executeQuery()){
				if(!rs.next()) {
					return "Your school is not registered";
					
				}
				
				else {
					String sql2 = "SELECT rejectedID FROM Rejected where emailAddress = ? AND schoolRegNo = ?";
					try(PreparedStatement stmt2 = Server.conn.prepareStatement(sql2)){
						stmt2.setString(1, emailAddress);
						stmt2.setInt(2, schRegNo);
						try(ResultSet rs2 = stmt2.executeQuery()){
							if(!rs2.next()) {
								//Result set  empty
								String sql3 = "SELECT userName FROM Participant where username = ?";
								try(PreparedStatement stmt3 = Server.conn.prepareStatement(sql3)){
									stmt3.setString(1,userName);
									try(ResultSet rs3 = stmt3.executeQuery()){
										if(!rs3.next()) {
											//Result set empty
											String sql4 = "SELECT userName FROM Applicant where username = ?";
											try(PreparedStatement stmt4 = Server.conn.prepareStatement(sql4)){
												stmt4.setString(1,userName);
												try(ResultSet rs4 = stmt4.executeQuery()){
													if(!rs4.next()) {
														/*create file object
														 * open a file input stream to read the file
														 * create a byte array to store read data
														 */
														File imageFile = new File(imagePath);
														try(FileInputStream fis = new FileInputStream(imageFile)){
															byte[] imageBytes = new byte[(int) imageFile.length()];
													        fis.read(imageBytes);

															// Hash the password using BCrypt
													        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

															DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define the date format
															LocalDate date = LocalDate.parse(dateOfBirth, formatter); // Parse the string into a LocalDate
													      
													        String sql5 = "INSERT INTO Applicant(schoolRegNo,emailAddress,userName,image,"
																	+ "firstName,lastName,password,dateOfBirth) VALUES(?,?,?,?,?,?,?,?)";
															 try(PreparedStatement stmt5 = Server.conn.prepareStatement(sql5)){
																 stmt5.setInt(1,schRegNo);
																 stmt5.setString(2,emailAddress);
																 stmt5.setString(3,userName);
																 stmt5.setBytes(4,imageBytes);
																 stmt5.setString(5,firstName);
																 stmt5.setString(6,lastName);
																 stmt5.setString(7,hashedPassword);
																 stmt5.setDate(8,java.sql.Date.valueOf(date));// Convert LocalDate to java.sql.Date
																 stmt5.executeUpdate();

																 String schName = rs.getString("schoolName");
																 String repEmail = rs.getString("emailAddress");
																 String repName = rs.getString("repName");
																 Email.notifySchoolRepresentative(repName,firstName,lastName, repEmail, schName);
																 return "Registration process complete";
											
															}
														}
														
														
														 
													}
													else {
														return "User name already taken";
													}
													
												}
											}
											
											
										}
										else {
											return "User name already taken";
											
										}
									}
									
								}
							}
							else {
								return "You cannot register under this school because you were rejected ";
							}
							
						}
						
					}
					
					
				}
			}
			
		}
	
	}

}
