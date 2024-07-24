package com.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

class Client {
	static Scanner scan;
	
	static void start() throws UnknownHostException, IOException {
		scan = new Scanner(System.in); // Initialize the Scanner here
		while(true) {
			System.out.println("Would you like to register or login?");
			System.out.println(" Register username  firstname lastname emailAddress date_of_birth(yy-mm-dd) school_registration_number password image_file.png");
			System.out.println(" Login username password");
			String Input1 = scan.nextLine();
			String[] Details = Input1.split("\\s+");
			
			switch(Details[0].toLowerCase()) {
			
			case "login":
				Login.login(Input1);
				return;// Exit after login attempt
					
			case "register":
				boolean success = Register.register(Input1);
				
				if(success) {
					return; // Exit after successful registration or Your school is not registered
				}
				break; // Continue loop on failed registration
					
			default:
				System.out.println("Invalid input");
					
			}
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("Welcome to the International Education Mathematics Competition");
		start();

	}

}
