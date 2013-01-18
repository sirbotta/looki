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
import javax.faces.bean.SessionScoped;

/**
 *
 * @author simone
 */
@ManagedBean//(eager=true)
@SessionScoped
public class AuthBean implements Serializable {

    /**
     * Creates a new instance of AuthBean
     */
    @ManagedProperty(value = "#{dbmanager}")
    private DbmanagerBean dbmanager;
    private String username;
    private String password;
    private User user;

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

    public String ValidateUser() throws SQLException {
        user = dbmanager.findUser(username, password);
        if (user == null) {
            password = null;
            return "login";
        }
        if (user.isAdmin_role()) {
            return "adminPage";
        } else {
            return "landingPage";
        }
    }

    public String getUserGreatings() {
        if (user == null) {
            return "Ciao ospite";
        } else {
            return "Ciao " + user.getUsername();
        }
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param dbmanager the dbmanager to set
     */
    public void setDbmanager(DbmanagerBean dbmanager) {
        this.dbmanager = dbmanager;
    }
}
