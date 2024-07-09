package recess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LoginRegister {
	private static final String Login = "login";
	private static final String Register = "register";
	private static final String Participant = "participant";
	private static final String SchRep = "schRep";
	private static final String NotRegistered = "Not registered";
	private static final String Applicant = "applicant";
	private static final String Taken = "taken";
	static Scanner scan = new Scanner(System.in);
	static void loginRegister() throws UnknownHostException, IOException {
		while(true) {
			System.out.println("Would you like to register or login?");
			System.out.println(" Register username  firstname lastname emailAddress date_of_birth(yy-mm-dd) school_registration_number password image_file.png");
			System.out.println(" Login username password");
			String Input1 = scan.nextLine();
			String[] Details = Input1.split("\\s+");
			
			switch(Details[0].toLowerCase()) {
			case Login:
				if(Details.length == 3) {
					sendDetails(Input1);
					return;
				}
				else {
					System.out.println("Invalid login details. Please provide username and password.");
				}
				break;
				
			case Register:
				if(Details.length == 9 && isValidEmail(Details[4]) && isValidDate(Details[5]) && isInteger(Details[6]) && isValidFileFormat(Details[8]) ) {
					sendDetails(Input1);
					return;
				}
				
				else {
					System.out.println("Invalid registration details. Please provide valid and all required information.");
				}
				break;
				
			default:
				System.out.println("Invalid input");
					
			}
			
		}
	}
	static void sendDetails(String enteredDetails) throws UnknownHostException, IOException {
		try(
				Socket soc = new Socket("localhost",1060);
				PrintWriter pw = new PrintWriter(soc.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()))
	            		){
			pw.println(enteredDetails);
            readVerifiedDetails(in);	
		}
			
	}
	
	static void readVerifiedDetails(BufferedReader in) throws IOException {
		String result= in.readLine();
		
		if (result == null) {
            System.out.println("No response from server.");
            return;
        }
		
		switch(result) {
		case Participant:
			System.out.println("Login successfully");
			//call view challenges method
			break;
		
		case SchRep:
			System.out.println("Login successfully");
			//call view applicants method	
			break;
			
		case NotRegistered:
			System.out.println("Your school is not registered");	
			break;
			
		case Applicant:
			System.out.println("Registration process complete");
			break;
			
		case Taken:
			System.out.println("Username already taken");
			loginRegister();
			break;
			
		default:
			System.out.println("Wrong password or username");
			loginRegister();
		
		}
		
	}
	
	static boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
	
	static boolean isValidDate(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        try {
            LocalDate parsedDate = LocalDate.parse(date, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
	static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	static boolean isValidFileFormat(String fileName) {
        String[] validExtensions = {".png",".jpg"};
        for (String extension : validExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }

        return false;
    }
		
		

	public static void main(String[] args) throws UnknownHostException, IOException {
		loginRegister();

	}

}
