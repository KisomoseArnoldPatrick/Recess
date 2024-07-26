package com.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SchoolRep {

	void createConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ies_schema","root","");
		System.out.println("Database Connection success");
		String sql = "INSERT INTO schoolRepresentative (schoolName, schoolRegNo,district, adminID,userName,"
				+ " repName, emailAddress,password) VALUES (?,?,?,?,?,?,?,?)";
		//use PreparedStatement if you want to reuse the SQL query for multiple executions
		PreparedStatement pstmt = connn.prepareStatement(sql);
		/*The index starts from 1(not 0), so the first parameter has an index of 1, 
		 * the second parameter has an index of 2, and so on.
		 */
		 // Hash the password using BCrypt
		 String password = "701503070";
		String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

		pstmt.setString(1,"Mbarara Primary School");
		pstmt.setInt(2,41);
		pstmt.setString(3,"Mbarara");
		pstmt.setInt(4,17);
		pstmt.setString(5,"ArnoldPK");
		pstmt.setString(6,"Kisomose Arnold Patrick");
		pstmt.setString(7,"kisomose.arnoldpatrick@students.mak.ac.ug");
		pstmt.setString(8,hashedPassword);
		pstmt.addBatch();
		
		 // Hash the password using BCrypt
		 String password2 = "765809725";
        String hashedPassword2 = BCrypt.withDefaults().hashToString(12, password2.toCharArray());
		pstmt.clearParameters();
		pstmt.setString(1,"City Parents' School");
		pstmt.setInt(2,1);
		pstmt.setString(3,"Kampala");
		pstmt.setInt(4,1);
		pstmt.setString(5,"Lilly");
		pstmt.setString(6,"Isio Lily");
		pstmt.setString(7,"lilyime272@gmail.com");
		pstmt.setString(8,hashedPassword2);
		pstmt.addBatch();
		
		
		
		pstmt.clearParameters();
		//executeUpdate() - Executes the SQL statement in this PreparedStatement object
		int[] rowsAffected = pstmt.executeBatch();
		if(rowsAffected.length > 0) {
			System.out.println("Data inserted successfully!");
			System.out.println(rowsAffected.length+" rows inserted");
			/*for (int i : rowsAffected) {
			 * executeBatch() method returns an array of integers representing the number of affected 
			 * rows for each batch operation.
				System.out.println(i);
				
			}*/
		}
		else {
			/*The else block won’t execute because the exception handling takes precedence.
			 * else block maybe left out
			 */
			System.out.println("Failed to insert data.");
		}
		pstmt.close();
		connn.close();
		
		
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		SchoolRep test = new SchoolRep();
		test.createConnection();

	}

}
