package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Server {
	static Connection conn;
	static void  createConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ies_schema","root","");
		System.out.println("Database Connection success");
	}
	static void handleClient(Socket soc) {
		String response;
		try(
				// Initialize input and output streams for this client
			BufferedReader	br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			PrintWriter	pw = new PrintWriter(soc.getOutputStream(), true)
				){
			String readData = br.readLine();
			/*Splitting the input into separate components 
			 * and storing the components in an array.
			 * \\s: This represents a whitespace character (such as space, tab, or newline).
			 * +: This quantifier means “one or more occurrences.”
			 */
			String[] Details = readData.split("\\s+");
			switch(Details[0].toLowerCase()) {
			case "login":
				String userName = Details[1];
	            String password = Details[2];
	            response = Login.checkDetails(userName,password);
	            pw.println(response);
				break;
				
			case "register":
				response = Register.checkDetails(readData);
				pw.println(response);
				break;
				
			case "viewchallenges":
				response = ViewChallenge.viewChallenges();
				pw.println(response);
				break;
				
			case "viewapplicants":
				response = ViewApplicant.viewApplicants();
				pw.println(response);
				break;
				
			case "confirm":
				if(Details[1].equals("yes")) {
					String username = Details[2];
					response = ViewApplicant.confirmYes(username);
					pw.println(response);
					
				}else {
					//confirm no
					String user_name = Details[2];
					response = ViewApplicant.confirmNo(user_name);
					pw.println(response);
				}
				break;
			case "attemptchallenge":
				// Add your implementation for attemptChallenge here
				break;
				
				
			default:	
				 pw.println("Invalid operation.");
			}
		}catch (IOException | SQLException e) {
	            e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		
		try {
			createConnection();
			System.out.println("Waiting for clients....");
			
			try (ServerSocket ss = new ServerSocket(1060)) {
				/*The loop ensures that the server remains active
				 *  and responsive to multiple clients.
				 *  As soon as a client connects, the server
				 *   processes its request and then goes back to listening for more connections.
				 */
				while(true) {
					try (Socket soc = ss.accept()){
						System.out.println("Client connected");
						handleClient(soc);
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}catch(IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();	
			
		}finally {
			if(conn != null) {
				try {
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		

	}

}
