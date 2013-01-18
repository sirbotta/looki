/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.Auction;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author simone
 */
@ManagedBean
@ConversationScoped
public class AuctionBean implements Serializable{

    /**
     * Creates a new instance of AuctionBean
     */
    @Inject
    private Conversation conversation;
    @ManagedProperty(value = "#{dbmanager}")
    private DbmanagerBean dbmanager;
    @ManagedProperty(value = "#{param.auction_id}")
    private int auction_id; 
    private double increment;
    private Auction auction;
    
    
    @PostConstruct
    public void init() throws SQLException
    {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        
        if(auction==null){
        auction=dbmanager.findAuctionById(auction_id);
        }
        
    }
    
    public String makeABid(){
        if(increment>auction.getMin_increment()){
        auction.setActual_price(increment);
        }
        
        return "auctionPage";
    }
    

    /**
     * @param dbmanager the dbmanager to set
     */
    public void setDbmanager(DbmanagerBean dbmanager) {
        this.dbmanager = dbmanager;
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
     * @return the auction
     */
    public Auction getAuction() {
        return auction;
    }

    /**
     * @param auction the auction to set
     */
    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    /**
     * @return the increment
     */
    public double getIncrement() {
        return increment;
    }

    /**
     * @param increment the increment to set
     */
    public void setIncrement(double increment) {
        this.increment = increment;
    }
    
    
    
}
