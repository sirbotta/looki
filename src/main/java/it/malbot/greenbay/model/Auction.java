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
public class Auction implements Serializable{
    private int id;
    private int user_id,category_id;
    private String description,url_image,username,category_name;
    private Double initial_price,min_increment,actual_price,delivery_price;
    private Timestamp due_date,insertion_date;
    private boolean closed;

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
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
    public Double getInitial_price() {
        return initial_price;
    }

    /**
     * @param initial_price the initial_price to set
     */
    public void setInitial_price(Double initial_price) {
        this.initial_price = initial_price;
    }

    /**
     * @return the min_increment
     */
    public Double getMin_increment() {
        return min_increment;
    }

    /**
     * @param min_increment the min_increment to set
     */
    public void setMin_increment(Double min_increment) {
        this.min_increment = min_increment;
    }

    /**
     * @return the delivery_price
     */
    public Double getDelivery_price() {
        return delivery_price;
    }

    /**
     * @param delivery_price the delivery_price to set
     */
    public void setDelivery_price(Double delivery_price) {
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
     * @return the insertion_date
     */
    public Timestamp getInsertion_date() {
        return insertion_date;
    }

    /**
     * @param insertion_date the insertion_date to set
     */
    public void setInsertion_date(Timestamp insertion_date) {
        this.insertion_date = insertion_date;
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
     * @return the category_name
     */
    public String getCategory_name() {
        return category_name;
    }

    /**
     * @param category_name the category_name to set
     */
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    /**
     * @return the actual_price
     */
    public Double getActual_price() {
        return actual_price;
    }

    /**
     * @param actual_price the actual_price to set
     */
    public void setActual_price(Double actual_price) {
        this.actual_price = actual_price;
    }

    /**
     * @return the closed
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * @param closed the closed to set
     */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    
}
