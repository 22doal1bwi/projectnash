package de.projectnash.application.util;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import de.projectnash.entities.User;

public class EmailUtility {
	
	private static String USER_NAME = "simplecert.nash";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "eierkuchen4"; // GMail password
    private static String RECIPIENT = "jonathan.schlotz@gmx.de";

    private static final String SUBJECT_PASSWORD_RESET = "Ihr Passwort wurde zurückgesetzt";
    private static final String SUBJECT_CERTIFICATE_EXPIRES = "Erinnerung: Verlängern Sie jetzt Ihr Zertifikat";
    
    public static void main(String[] args, User user, EmailSubject subjectOfeMail) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject;
        String body;
        if(EmailSubject.PASSWORD_RESET.equals(subjectOfeMail)){
        	  subject = SUBJECT_PASSWORD_RESET;
              body = "Hallo " + user.getFirstName() + " " + user.getLastName() + ", \n ihr neues Passwort lautet: " + "123456";
        } else {
        	 subject = SUBJECT_CERTIFICATE_EXPIRES;
        	 body = "Hallo " + user.getFirstName() + " " + user.getLastName() + ", \n ihr Zertifikat läuft in Kürze ab. Bitte verlängern Sie es schnellstmöglich.";
        }
       

        sendFromGMail(from, pass, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}