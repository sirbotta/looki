/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.User;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;

/**
 *
 * @author simone
 */
@ManagedBean
@RequestScoped
public class SubscriptionBean implements Serializable{

    /**
     * Creates a new instance of SubscriptionBean
     */
    @ManagedProperty(value = "#{dbmanager}")
    private DbmanagerBean dbmanager;
    
    @ManagedProperty(value = "#{mailer}")
    private MailerBean mailer;
    
    private String username,password,mail,address;
    
    
    /**
     * @param dbmanager the dbmanager to set
     */
    public void setDbmanager(DbmanagerBean dbmanager) {
        this.dbmanager = dbmanager;
    }

    

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param mailer the mailer to set
     */
    public void setMailer(MailerBean mailer) {
        this.mailer = mailer;
    }
    
    
    public String Subscript() throws SQLException, MessagingException
    {    
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setMail(mail);
        u.setAddress(address);
        
        String welcome_message=
            "Benvenuto "+u.getUsername()+" in Green bay ";
        if(dbmanager.insertUser(u)!=0){
            mailer.SendMail(u.getMail(), "Registrazione GREENbay", welcome_message);
            return "forceLoginPage";
        }
        else
        {
            return "subscriptionPage";
        }           
    }

    
    
    
    
    
}
