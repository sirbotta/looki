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
public class Sell implements Serializable{
    private int id, seller_id, buyer_id, auction_id;
    private Timestamp sell_date;
    private double final_price,tax;

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
     * @return the seller_id
     */
    public int getSeller_id() {
        return seller_id;
    }

    /**
     * @param seller_id the seller_id to set
     */
    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    /**
     * @return the buyer_id
     */
    public int getBuyer_id() {
        return buyer_id;
    }

    /**
     * @param buyer_id the buyer_id to set
     */
    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
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
     * @return the sell_date
     */
    public Timestamp getSell_date() {
        return sell_date;
    }

    /**
     * @param sell_date the sell_date to set
     */
    public void setSell_date(Timestamp sell_date) {
        this.sell_date = sell_date;
    }

    /**
     * @return the final_price
     */
    public double getFinal_price() {
        return final_price;
    }

    /**
     * @param final_price the final_price to set
     */
    public void setFinal_price(double final_price) {
        this.final_price = final_price;
    }

    /**
     * @return the tax
     */
    public double getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(double tax) {
        this.tax = tax;
    }
    
}
