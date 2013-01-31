/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.Auction;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;
import org.quartz.SchedulerException;

/**
 *
 * @author simone
 */
@ManagedBean
@RequestScoped
public class InsertAuctionBean implements Serializable{

    @ManagedProperty(value = "#{dbmanager}")
    private DbmanagerBean dbmanager;
    @ManagedProperty(value = "#{mailer}")
    private MailerBean mailer;
    @ManagedProperty(value = "#{scheduler}")
    private SchedulerBean scheduler;
    @ManagedProperty(value = "#{authBean}")
    private AuthBean authBean;
    private int category_id;
    private String due_code;
    final long minuteInMillis = 60L * 1000L;
    final long dayInMillis = 24L * 60L * 60L * 1000L;
    final long weekInMillis = 7L * 24L * 60L * 60L * 1000L;
    private String description, url_image="na.jpg";
    private double initial_price, min_increment, delivery_price;
    private Timestamp due_date;

    public String submitNewAuction() throws SchedulerException, MessagingException, SQLException {
        //creato un'asta
        Auction a = new Auction();
        //setto i parametri
        a.setUser_id(authBean.getUser().getId());
        a.setCategory_id(category_id);
        a.setDescription(description);
        a.setUrl_image(url_image);
        a.setInitial_price(initial_price);
        a.setMin_increment(min_increment);
        a.setActual_price(initial_price);
        a.setDelivery_price(delivery_price);

        if (getDue_code().equals("1minute")) {
            due_date = new Timestamp(new Date().getTime() + minuteInMillis);
        } else if (getDue_code().equals("1day")) {
            due_date = new Timestamp(new Date().getTime() + dayInMillis);
        } else {
            due_date = new Timestamp(new Date().getTime() + weekInMillis);
        }
        
        a.setDue_date(due_date);
        
        //inserisco i dati a DB
        
        int key=dbmanager.insertAuction(a);
   
        //genero un date da un timestamp
        Date closeDate = new Date(due_date.getTime());
        
        
        if(key!=0)
        {
        
        //creo un job che chiuderà l'asta
        //@TODO job da scrivere correttamente
        scheduler.closeAuctionAt(closeDate, key);

        //mando una mail di conferma
        mailer.SendMail(authBean.getUser().getMail(),
                "Asta creata con successo",
                "L'asta " + description + " scadrà il"
                + due_date.toString());

        }
        
        return "landingPage";
    }
   

    /**
     * @param dbmanager the dbmanager to set
     */
    public void setDbmanager(DbmanagerBean dbmanager) {
        this.dbmanager = dbmanager;
    }

    /**
     * @param mailer the mailer to set
     */
    public void setMailer(MailerBean mailer) {
        this.mailer = mailer;
    }

    /**
     * @return the category_id
     */
    public int getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id the category_id to set
     */
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the url_image
     */
    public String getUrl_image() {
        return url_image;
    }

    /**
     * @param url_image the url_image to set
     */
    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    /**
     * @return the initial_price
     */
    public double getInitial_price() {
        return initial_price;
    }

    /**
     * @param initial_price the initial_price to set
     */
    public void setInitial_price(double initial_price) {
        this.initial_price = initial_price;
    }

    /**
     * @return the min_increment
     */
    public double getMin_increment() {
        return min_increment;
    }

    /**
     * @param min_increment the min_increment to set
     */
    public void setMin_increment(double min_increment) {
        this.min_increment = min_increment;
    }

    /**
     * @return the delivery_price
     */
    public double getDelivery_price() {
        return delivery_price;
    }

    /**
     * @param delivery_price the delivery_price to set
     */
    public void setDelivery_price(double delivery_price) {
        this.delivery_price = delivery_price;
    }

    /**
     * @return the due_date
     */
    public Timestamp getDue_date() {
        return due_date;
    }

    /**
     * @param due_date the due_date to set
     */
    public void setDue_date(Timestamp due_date) {
        this.due_date = due_date;
    }

    /**
     * @param scheduler the scheduler to set
     */
    public void setScheduler(SchedulerBean scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * @param authBean the authBean to set
     */
    public void setAuthBean(AuthBean authBean) {
        this.authBean = authBean;
    }

    /**
     * @return the due_code
     */
    public String getDue_code() {
        return due_code;
    }

    /**
     * @param due_code the due_code to set
     */
    public void setDue_code(String due_code) {
        this.due_code = due_code;
    }
}
