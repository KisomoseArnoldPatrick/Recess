package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Register {
	static boolean register(String input) throws UnknownHostException, IOException {
		String[] Details = input.split("\\s+");
        String validationResponse = validateDetails(Details);
		if(!validationResponse.equals("Valid") ) {
            System.out.println("Invalid registration details: " + validationResponse);
            return false;
        }else{
            String response = sendDetails(input);
			System.out.println(response);
			if(response.equals("User name already taken")) {
				return false;//  to give a user another chance
            }
				
			return true;
        }

    }
			
    static String validateDetails(String[] details) {
        if (details.length != 9) return "Incorrect number of details.";
        if(!isStringLengthValid(details[1], 20)) return "User name too long";
        if(!isStringLengthValid(details[2], 10)) return "first name too long";
        if(!isStringLengthValid(details[3], 10)) return "last name too long";
        if (!isValidEmail(details[4])) return "Invalid email.";
        if (!isValidDate(details[5])) return "Invalid date.";
        if (!isInteger(details[6])) return "Expected an integer.";
        if (!isValidFileFormat(details[8])) return "Invalid file format.";
        return "Valid";
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
            LocalDate.parse(date, dateFormatter);
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

    // Method to check if the string length is valid
     static boolean isStringLengthValid(String input, int maxLength) {
        return input.length() <= maxLength;
    }
	
	static String sendDetails(String enteredDetails) throws UnknownHostException, IOException {
		try(
				Socket soc = new Socket("localhost",1060);
				PrintWriter pw = new PrintWriter(soc.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()))
	            		){
			pw.println(enteredDetails);
			return in.readLine();	
		}
			
	}
		

}
