/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import java.io.Serializable;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author simone
 */
@ManagedBean(name = "mailer" )//, eager=true)
@ApplicationScoped
public class MailerBean implements Serializable {

    /**
     * Creates a new instance of MailerBean
     */
    private Session session;
    private String username;
    private String password;

    //@PostConstruct
    public void connect() {
        username = "greenbay.noreply@gmail.com";
        password = "greenbay2013";
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void SendMail(String recipient, String subject, String text) throws MessagingException {
        
        connect();
        // — Create a new message –
        Message msg = new MimeMessage(session);
        // — Set the FROM and TO fields –
        msg.setFrom(new InternetAddress(username + ""));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient, false));
        msg.setSubject(subject);
        msg.setText(text);
        msg.setSentDate(new Date());
        Transport.send(msg);
    }
}
