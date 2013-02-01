/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.Auction;
import it.malbot.greenbay.model.Sell;
import it.malbot.greenbay.model.User;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import org.quartz.SchedulerException;

/**
 *
 * @author simone
 */
@ManagedBean
@SessionScoped
public class AdminBean implements Serializable {

    @ManagedProperty(value = "#{dbmanager}")
    private DbmanagerBean dbmanager;
    @ManagedProperty(value = "#{mailer}")
    private MailerBean mailer;
    @ManagedProperty(value = "#{scheduler}")
    private SchedulerBean scheduler;
    @ManagedProperty(value = "#{authBean}")
    private AuthBean authBean;
    private List<Auction> auctions;
    private List<Sell> sells;
    private Auction auction;

    //visualizzare le aste e fermarle
    public String goToOpenAuctions() throws SQLException {

        setAuctions(dbmanager.getAuctionOpen());
        return "adminOpenAuctionPage";
    }

    public String killAuction() throws SchedulerException, SQLException, MessagingException {


        //fermo lo scheduler
        scheduler.killAJob(auction.getId());

        //chiudo l'asta
        dbmanager.closeAuction(auction.getId());

        //trovo il seller
        User seller = dbmanager.findUser(auction.getUser_id());

        //notifico al venditore
        mailer.SendMail(seller.getMail(),
                "Asta " + auction.getId() + " CANCELLATA by ADMIN " + authBean.getUsername(),
                "L'asta " + auction.getId() + " è stata chiusa, ci scusiamo per il disagio. Non saranno applicate Tasse a seguito"
                + " dell'intervento.");

        //TODO da fare la spedizione a tutti i bidder
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Asta " + auction.getId() + " terminata con successo");
        FacesContext.getCurrentInstance().addMessage(null, fm);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        //ritorno alla pagina precedente e refillo le aste
        return goToOpenAuctions();


    }

    public String goToSellsData() throws SQLException {
        setSells(dbmanager.getSell());
        return "adminSellsDataPage";
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
     * @return the auctions
     */
    public List<Auction> getAuctions() {
        return auctions;
    }

    /**
     * @param auctions the auctions to set
     */
    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    /**
     * @return the sells
     */
    public List<Sell> getSells() {
        return sells;
    }

    /**
     * @param sells the sells to set
     */
    public void setSells(List<Sell> sells) {
        this.sells = sells;
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
}
