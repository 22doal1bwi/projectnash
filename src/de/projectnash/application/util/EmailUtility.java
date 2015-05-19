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
        String sender = USER_NAME;
        String senderPassword = PASSWORD;
        String recipient =  user.getEmailAddress();
        String subject;
        String body;
        
        if(EmailSubject.PASSWORD_RESET.equals(subjectOfeMail)){
        	  subject = SUBJECT_PASSWORD_RESET;
              body = "Hallo " + user.getFirstName() + " " + user.getLastName() + ",\n\nIhr neues Passwort lautet: " + user.getPassword();
        } else {
        	 subject = SUBJECT_CERTIFICATE_EXPIRES;
        	 body = "Hallo " + user.getFirstName() + " " + user.getLastName() + ",\n\nIhr Zertifikat läuft in Kürze ab. Bitte verlängern Sie es schnellstmöglich.";
        }
       

        setupGMailConnection(sender, senderPassword, recipient, subject, body);
    }

    /**
     * Sets up the connection for GMail-Account and sends the email with the specified data.
     * 
     * @param sender The {@link String} that represents the email address of the sender.
     * @param senderPassword The {@link String} that represents the password of the sender.
     * @param recipient The {@link String} that represents the recipient of the email.
     * @param subject The {@link String} that represents the subject of the email.
     * @param body The {@link String} that represents the content of the email.
     */
    private static void setupGMailConnection(String sender, String senderPassword, String recipient, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", sender);
        props.put("mail.smtp.password", senderPassword);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(sender));
            InternetAddress toAddress = new InternetAddress(recipient);
            message.addRecipient(Message.RecipientType.TO, toAddress);
          

            message.setSubject(subject);
            message.setContent("CONTENT", "text/html; charset=UTF-8");
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, sender, senderPassword);
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