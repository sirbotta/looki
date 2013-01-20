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
public class Auction_Bid implements Serializable{
    
    private int id,auction_id,user_id;
    private Timestamp bid_date;
    private double offer;

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
     * @return the auction_id
     */
    public int getAuction_id() {
        return auction_id;
    }

    /**
     * @param auction_id the auction_id to set
     */
    public void setAuction_id(int auction_id) {
        this.auction_id = auction_id;
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
     * @return the bid_date
     */
    public Timestamp getBid_date() {
        return bid_date;
    }

    /**
     * @param bid_date the bid_date to set
     */
    public void setBid_date(Timestamp bid_date) {
        this.bid_date = bid_date;
    }

    /**
     * @return the offer
     */
    public double getOffer() {
        return offer;
    }

    /**
     * @param offer the offer to set
     */
    public void setOffer(double offer) {
        this.offer = offer;
    }
}
