package group825.vetapp2.users;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * This class provides methods that sends email
 */
public class EmailModel {
	/**
     * for singleton implementation
     */
    private static EmailModel singleInstance;


    /**
     * username for sender email
     */
    private String username;

    /**
     * password for sender email
     */
    private String password;

    /**
     * email address of the sender
     */
    private String sender;

    /**
     * properties of the email
     */
    private Properties properties;

    /**
     * Session object for the email
     */
    private Session session;

    /**
     * Contains the content of the email
     */
    private Message message;

    /**
     * class variable: ArrayList<String>
     * Stores emails of recipients
     */
    private ArrayList<String> recipients;

    /**
     * Private constructor of EmailModel for singleton implementation
     */
    private EmailModel(){
        this.username="ensf607.825@gmail.com";
        this.password = "group825";
        this.sender= username;
        this.properties = new Properties();
        
        this.properties.put("mail.smtp.starttls.enable", "true");
        this.properties.put("mail.smtp.auth", "true");
        this.properties.put("mail.smtp.host", "smtp.gmail.com");
        this.properties.put("mail.smtp.port", "587");
        this.properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        this.session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }
    
    /**
     * Get single instance of EmailModel for singleton implementation
     * @return EmailModel
     */
    public static EmailModel getSingleInstance()
    {
        if(singleInstance==null)
        	singleInstance=new EmailModel();
        return singleInstance;
    }
    
    /**
     * Sends an email to every health technician selected
     * @param emailAddresses = ArrayList of all the selected technician's emails
     * @param subject = Subject of the email
     * @param text = body of the email
     */
    public void sendEmail(ArrayList<String> emailAddresses, String subject, String text)
    {
    	
        try {
        	this.message = new MimeMessage(this.session);
            this.message.setFrom(new InternetAddress(this.sender));
            
        	if (emailAddresses.size()>0) {
        		for (String recipient: emailAddresses) {
        			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        		}
                message.setSubject(subject);
                message.setText(text);
                Transport.send(this.message);
        	}else {
        		System.out.println("No recipients selected. No email will be sent out.");
        	}
            
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending out an email");
        }
    }
}
