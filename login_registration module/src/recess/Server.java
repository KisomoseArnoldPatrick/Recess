package recess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Server {
	private static final String Login = "login";
	private static final String Register = "register";
	static Connection conn;
	static void  createConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/recessdb","root","");
		System.out.println("Database Connection success");
	}
	static void handleClient(Socket soc) {
		try(
				// Initialize input and output streams for this client
			BufferedReader	br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			PrintWriter	pw = new PrintWriter(soc.getOutputStream(), true)
				){
			String readData = br.readLine();
			/*spliting the input into separate components 
			 * and storing the components in an array.
			 * \\s: This represents a whitespace character (such as space, tab, or newline).
			 * +: This quantifier means “one or more occurrences.”
			 */
			String[] Details = readData.split("\\s+");
			switch(Details[0].toLowerCase()) {
			case Login:
				String userName = Details[1];
	            String password = Details[2];
	            checkDetails(userName,password,pw);
				break;
				
			case Register:
				checkDetails(readData,pw);
				break;
				
			default:	
				
			}
		}catch (IOException | SQLException e) {
	            e.printStackTrace();
			}
	}
	
	
	static boolean checkDetails(String userName, String password,PrintWriter pw) throws SQLException {
		String sql = "SELECT userName, password FROM Participant WHERE userName = ? AND password = ?";
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, userName);
			stmt.setString(2, password);
			try(ResultSet rs = stmt.executeQuery()){
				if(!rs.next()) {
					 // ResultSet is empty, check in SchoolRepresentative table
					String sql2 = "SELECT userName, password FROM SchoolRepresentative WHERE userName = ? AND password = ?";
					try(PreparedStatement stmt2 = conn.prepareStatement(sql2)){
						stmt2.setString(1, userName);
						stmt2.setString(2, password);
						try(ResultSet rs2 = stmt.executeQuery()){
							if(!rs2.next()) {
								//System.out: The OutputStream to which the PrintWriter will send its output. 
								pw.println("incorrect");
								return false;
								
							}
							else {
								pw.println("schRep");
								System.out.println("SSS");
								return true;
							}
						}
					}
				}		
				else {
					pw.println("participant");
					System.out.println("PPPPPP");
					return true;
					}
			}
		}
		
	}
	
	static boolean checkDetails(String registrationDetails, PrintWriter pw) throws SQLException, IOException {
		String[] Details = registrationDetails.split("\\s+");
		String userName =Details[1];
		String firstName =Details[2];
		String lastName =Details[3];
		String emailAddress =Details[4];
		String dateOfBirth =Details[5];
		String schRegNo =Details[6];
		String password = Details[7];
		String imagePath =Details[8];
		String sql = "SELECT schoolRegNo FROM SchoolRepresentative WHERE schoolRegNo = ? ";
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1,schRegNo);
			try(ResultSet rs = stmt.executeQuery()){
				if(!rs.next()) {
					pw.println("Not registered");
					return false;
					
				}
				
				else {
					String sql2 = "SELECT userName FROM Participant where username = ?";
					try(PreparedStatement stmt2 = conn.prepareStatement(sql2)){
						stmt2.setString(1,userName);
						try(ResultSet rs2 = stmt2.executeQuery()){
							if(!rs2.next()) {
								String sql3 = "SELECT userName FROM Applicant where username = ?";
								try(PreparedStatement stmt3 = conn.prepareStatement(sql3)){
									stmt3.setString(1,userName);
									try(ResultSet rs3 = stmt3.executeQuery()){
										if(!rs3.next()) {
											
											File imageFile = new File(imagePath);
											try(FileInputStream fis = new FileInputStream(imageFile)){
												byte[] imageBytes = new byte[(int) imageFile.length()];
										        fis.read(imageBytes);
										        
										        String sql4 = "INSERT INTO Applicant(schoolRegNo,emailAddress,userName,image,"
														+ "firstName,lastName,password,dateOfBirth) VALUES(?,?,?,?,?,?,?,?)";
												 try(PreparedStatement stmt4 = conn.prepareStatement(sql4)){
													 stmt4.setString(1,schRegNo);
													 stmt4.setString(2,emailAddress);
													 stmt4.setString(3,userName);
													 stmt4.setBytes(4,imageBytes);
													 stmt4.setString(5,firstName);
													 stmt4.setString(6,lastName);
													 stmt4.setString(7,password);
													 stmt4.setString(8,dateOfBirth);
													 stmt4.executeUpdate();
													 pw.println("applicant");
													 return true;
								
												}
											}
											
											
											 
										}
										else {
											pw.println("taken");
											return false;
										}
										
									}
								}
								
								
							}
							else {
								pw.println("taken");
								return false;
								
							}
						}
						
					}
				}
			}
			
		}
	
	}

	public static void main(String[] args){
		// TODO Auto-generated method stub
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
			
			
	
				
				
/*the resource initialization (br, pw, soc) should be moved inside the try block, specifically 
 * right after accepting a new client connection (ss.accept()). This ensures that:
 * Resources are instantiated fresh for each client connection.
 * If an exception occurs during resource initialization, it can be caught immediately and handled appropriately.
 * 
 * !ss.isClosed() is used as a condition in an if statement to check 
 * whether the ServerSocket (ss) is still open and able to accept new client connections. 
 * If ss.isClosed() returns false (i.e., the ServerSocket is not closed), then the condition 
 * !ss.isClosed() will evaluate to true, indicating that the ServerSocket is still open.
 * 
 * Including the closing of br, pw, and soc inside the finally block ensures that these 
 * resources are properly closed regardless of whether an exception occurs or not.
 * 
 * Closing Sequence: Ensure that resources are closed in the reverse order of their creation. 
 * This helps in preventing dependency issues between resources.
 */
