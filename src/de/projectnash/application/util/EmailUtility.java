package de.projectnash.application.util;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import de.projectnash.entities.User;

/**
 * This class provides all necessary methods in order to send an email to the specified {@link User}.
 * @author Silvio D'Alessandro
 *
 */
public class EmailUtility {
	
	/** GMail user name (just the part before '@googlemail.com'). */
	private static String USER_NAME = "simplecert.nash";  
	
	/** GMail password. */
    private static String PASSWORD = "eierkuchen4";
    
    /** The two subject cases. */
    private static final String SUBJECT_PASSWORD_RESET = "Ihr Passwort wurde zurückgesetzt";
    private static final String SUBJECT_CERTIFICATE_EXPIRES = "Erinnerung: Verlängern Sie jetzt Ihr Zertifikat";
    
    /**
     * Sends an email to the specified {@link User} with the entered subject.
     * 
     * @param user The {@link User} who will receive the email.
     * @param subjectOfeMail The {@link EmailSubject} that represents the subject of the email.
     */
    public static void sendMail(User user, EmailSubject subjectOfeMail) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { user.getEmailAddress() };
        String subject;
        String body;
        
        if(EmailSubject.PASSWORD_RESET.equals(subjectOfeMail)){
        	  subject = SUBJECT_PASSWORD_RESET;
              body = "Hallo " + user.getFirstName() + " " + user.getLastName() + ",\n\nIhr neues Passwort lautet: " + user.getPassword();
        } else {
        	 subject = SUBJECT_CERTIFICATE_EXPIRES;
        	 body = "Hallo " + user.getFirstName() + " " + user.getLastName() + ",\n\nIhr Zertifikat läuft in Kürze ab. Bitte verlängern Sie es schnellstmöglich.";
        }
       

        setupGMailConnection(from, pass, to, subject, body);
    }

    /**
     * Sets up the connection for GMail-Account and sends the email with the specified data.
     * 
     * @param from The {@link String} that represents the email address of the sender.
     * @param pass The {@link String} that represents the password of the sender.
     * @param to The {@link String} that represents the recipients of the email.
     * @param subject The {@link String} that represents the subject of the email.
     * @param body The {@link String} that represents the content of the email.
     */
    private static void setupGMailConnection(String from, String pass, String[] to, String subject, String body) {
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

            /** To get the array of addresses. */
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setContent("CONTENT", "text/html; charset=utf-8");
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