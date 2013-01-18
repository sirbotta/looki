/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author simone
 */
public class User implements Serializable{
 
	private int id;
	private String username,password,address,mail;
        private Timestamp registration_date;
        private boolean admin_role;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the registration_date
     */
    public Timestamp getRegistration_date() {
        return registration_date;
    }

    /**
     * @param registration_date the registration_date to set
     */
    public void setRegistration_date(Timestamp registration_date) {
        this.registration_date = registration_date;
    }

    /**
     * @return the admin_role
     */
    public boolean isAdmin_role() {
        return admin_role;
    }

    /**
     * @param admin_role the admin_role to set
     */
    public void setAdmin_role(boolean admin_role) {
        this.admin_role = admin_role;
    }
    
}
