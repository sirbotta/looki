/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.User;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
            FacesMessage fm = new FacesMessage("Username o password errati");
            FacesContext.getCurrentInstance().addMessage("Errore", fm);
            return "login";
        }
        if (user.isAdmin_role()) {
            return "adminPage";
        } else {
            return "landingPage";
        }
    }

    public String InvalidateUser() {
        user = null;
        password = null;
        username = null;

        return "login";
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
    //fa redirect se l'utente non è un admin

    public void isAdmin() {

        if (user == null && user.isAdmin_role()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Utente non amministratore");
            FacesContext.getCurrentInstance().addMessage("Errore", fm);

            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();

            nav.performNavigation("login");
        }
    }
    //fa redirect se l'utente non è loggato

    public void isLogged() {

        if (user == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Bisogna effettuare il login prima di eseguire l'azione");
            FacesContext.getCurrentInstance().addMessage("Errore", fm);
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();

            nav.performNavigation("login");
        }
    }
}
