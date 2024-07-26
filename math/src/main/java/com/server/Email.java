package com.server;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


class Email {

    // General method to send email
    static void sendMail(String recipient, String subject, String messageBody) {
        String username = "groupimak@gmail.com";
        String password = "wabb zbkb blkl fgtq";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(messageBody);
            

            Transport.send(message);

            System.out.println("Email sent successfully to " + recipient);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    static void notifySchoolRepresentative(String schRepName,String firstName, String lastName, String repEmail,String schName) {
    	String subject = "Confirmation of Pupil Registration for National Mathematics Competition";
    	String messageBody = String.format(
    			"Dear "+schRepName+",\n\n"+
    			"I hope this email finds you well.\n\n" +
    		     "We are pleased to inform you that a pupil, "+firstName+" "+lastName+  " has successfully registered under "+schName+" to participate in the upcoming National Mathematics Competition organized by the International Education Services.\n\n" +
    		       "The competition aims to foster mathematical skills and encourage young minds to excel in this essential subject. We are excited to see the talents from "+schName+" showcase their abilities on a national platform.\n\n" +
    		       "We kindly ask you to confirm the applicant by logging into the system via the command line interface." +
    	            "Should you have any questions or require further information, please do not hesitate to contact us.\n\n" +
    	            "Thank you for your support and cooperation. We look forward to an exciting and successful competition.\n\n" +
    	            "Best regards,\n\n" +
    	            "International Education Services"
    	            
    			);
    	sendMail(repEmail, subject, messageBody);
    }
    
    static void notifyRejectedApplicant( String firstName, String lastName,String applicantEmail) {
    	String subject = "Notification of Registration Status for National Mathematics Competition";
    	String messageBody = String.format(
    			"Dear "+ firstName+" "+lastName+",\n\n"+
    			"I hope this email finds you well.\n\n" +
    			"We regret to inform you that your registration for the National Mathematics Competition,"
    			+ " organized by the International Education Services, has not been confirmed by your school representative. As a result, you will not be"
    			+ " able to participate in this year's competition.\n\n" +
    			"We understand that this may be disappointing news. Please feel free to contact your school representative to "
    			+ "understand more about their decision. Should you have any questions or require further assistance from our side, do not hesitate to contact us.\n\n" +
                "We appreciate your interest in the National Mathematics Competition and encourage you to continue pursuing excellence in mathematics."
                + " We hope to see your participation in future events.\n\n" +
                "Thank you for your understanding.\n\n" +
                "Best regards,\n\n" +
                "International Education Services"
                );
    	sendMail(applicantEmail, subject, messageBody);
    			
    	
    }
    
    static void notifyAcceptedApplicant(String applicantEmail, String firstName, String lastName) {
    	String subject = "Confirmation of Participation in National Mathematics Competition";
    	String messageBody = String.format(
    			"Dear "+ firstName+" "+lastName+",\n\n"+
    			"I hope this email finds you well.\n\n" +
    			 "We are excited to inform you that your registration for the National Mathematics Competition, organized by the International Education Services, has been confirmed by your school representative. Congratulations on becoming"
    			 + " a participant in this prestigious event!\n\n" +
    			 "Please ensure you are well-prepared and familiar with the competition guidelines. Should you have any questions or require further assistance, do not hesitate to contact us.\n\n" +
    	          "We wish you the best of luck in the competition and look forward to seeing your excellent performance.\n\n" +
    	          "Best regards,\n\n" +
    	          "International Education Services\n"
    	          
    			);
    	sendMail(applicantEmail, subject, messageBody);
        
        }

}

	
