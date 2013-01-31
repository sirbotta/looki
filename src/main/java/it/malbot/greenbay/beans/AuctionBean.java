/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.Auction;
import it.malbot.greenbay.model.Auction_Bid;
import it.malbot.greenbay.model.User;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author simone
 */
@ManagedBean
@SessionScoped
public class AuctionBean implements Serializable {

    /**
     * Creates a new instance of AuctionBean
     */
    //@Inject
    //private Conversation conversation;
    @ManagedProperty(value = "#{dbmanager}")
    private DbmanagerBean dbmanager;
    @ManagedProperty(value = "#{authBean}")
    private AuthBean authBean;
    //@ManagedProperty(value = "#{param.auction_id}")
    private int auction_id;
    //@ManagedProperty(value = "#{param.bid_value}")
    private double bid_value;
    private double min_increment;//incremento minimo attaccato alle pagine
    private Auction auction;

    /*
     @PostConstruct
     public void init() throws SQLException {
     // if (conversation.isTransient()) {
     //   conversation.begin();
     //}

     auction = dbmanager.findAuctionById(auction_id);
     //genero il minimo
     setMin_increment(roundToCent(auction.getMin_increment() + auction.getActual_price()));

     }
     */
    public String goToAuctionPage() throws SQLException {
        auction_id = auction.getId();
        setMin_increment(roundToCent(auction.getMin_increment() + auction.getActual_price()));
        return "auctionPage";
    }

    public String goToConfirmBid() throws SQLException {

        //controllo che sia la bid sia superiore o uguale al minimo imposto
        Double minimo = roundToCent(auction.getMin_increment() + auction.getActual_price());
        if (min_increment >= minimo) {
            return "confirmPage";
        } else {
            FacesMessage fm = new FacesMessage("Offerta troppo bassa");
            FacesContext.getCurrentInstance().addMessage("Errore", fm);
            return "picche";
        }
    }

    public String confirmBid() throws SQLException {

        bid_value = min_increment;

        User u = authBean.getUser();

        if (u.getId() == auction.getUser_id()) {
            FacesMessage fm = new FacesMessage("Non puoi puntare alle tue aste!");
            FacesContext.getCurrentInstance().addMessage("Errore", fm);
            return "picche";
        }




        Auction_Bid bestAuction_Bid;
        //recupero la puntata maggiore
        bestAuction_Bid = dbmanager.findMaxAuctionBidByAuctionID(auction_id);
        //se esiste 
        if (bestAuction_Bid != null) {
            //controllo se l'utente è lo stesso e se tenta di puntare più basso
            if (u.getId() == bestAuction_Bid.getUser_id() && bid_value <= bestAuction_Bid.getOffer()) {
                FacesMessage fm = new FacesMessage("Sei già il miglior offerente e la tua offerta è più bassa della precedente");
                FacesContext.getCurrentInstance().addMessage("Errore", fm);
                return "picche";
            }

            //inserisco la puntata e controllo che ci sia in db
            if (dbmanager.insertBid(u.getId(), auction_id, bid_value) != 0) {


                ///se l'offerta è maggiore della best
                if (bestAuction_Bid.getOffer() < bid_value) {

                    //aumento soltanto dell'incremento minimo
                    bid_value = roundToCent(auction.getMin_increment() + auction.getActual_price());


                    if (dbmanager.updateAuction(auction_id, u.getId(), bid_value) != 0) {
                        auction.setWinner_id(u.getId());
                        auction.setActual_price(bid_value);
                        setMin_increment(roundToCent(auction.getMin_increment() + bid_value));
                        return "auctionPage";
                    }
                } else//altrimenti se è minore aggiorno con la best incrementata del min
                {

                    //porto la offerta massima al bid_value+incremento
                    bid_value = roundToCent(bid_value + auction.getMin_increment());
                    //se la nuova bid_value è troppo alta rispetto all'offerta più alta
                    if (bid_value > bestAuction_Bid.getOffer()) {
                        //utilizzo l'offerta più alta e ignoro l'incremento
                        bid_value = bestAuction_Bid.getOffer();
                    }

                    if (dbmanager.updateAuction(auction_id, bestAuction_Bid.getUser_id(), bid_value) != 0) {
                        auction.setWinner_id(bestAuction_Bid.getUser_id());
                        auction.setActual_price(bid_value);
                        setMin_increment(roundToCent(auction.getMin_increment() + bid_value));
                        return "auctionPage";
                    }
                }


            }
        }else
        {
             //inserisco la puntata e controllo che ci sia in db
            if (dbmanager.insertBid(u.getId(), auction_id, bid_value) != 0){
                //aumento soltanto dell'incremento minimo
                bid_value = roundToCent(auction.getMin_increment() + auction.getActual_price());


                    if (dbmanager.updateAuction(auction_id, u.getId(), bid_value) != 0) {
                        auction.setWinner_id(u.getId());
                        auction.setActual_price(bid_value);
                        setMin_increment(roundToCent(auction.getMin_increment() + bid_value));
                        return "auctionPage";
                    }
            }
        }

        FacesMessage fm = new FacesMessage("Qualcosa è andato storto, riprovare");
        FacesContext.getCurrentInstance().addMessage("Errore", fm);

        return "picche";
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
    /**
     * @param authBean the authBean to set
     */
    public void setAuthBean(AuthBean authBean) {
        this.authBean = authBean;
    }

    /**
     * @return the bid_value
     */
    public double getBid_value() {
        return bid_value;
    }

    /**
     * @param bid_value the bid_value to set
     */
    public void setBid_value(double bid_value) {
        this.bid_value = bid_value;
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

    //trimmare ai centesimi
    private Double roundToCent(Double value) {
        return (double) Math.round(value * 100) / 100;
    }
}
