/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.Auction;
import it.malbot.greenbay.model.Category;
import it.malbot.greenbay.model.Sell;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author simone
 */
/// per lo scope custom usare
//in creazione <f:actionListener type="it.malbot.greenbay.helper.CreateCustomScope" />
//in distruzione <f:actionListener type="it.malbot.greenbay.helper.DestroyCustomScope" />
@ManagedBean
//@CustomScoped("#{CUSTOM_SCOPE}")
//@ConversationScoped
@SessionScoped
public class searchBean implements Serializable {

    /**
     * Creates a new instance of searchBean
     */
    //@Inject
    //private Conversation conversation;
    @ManagedProperty(value = "#{dbmanager}")
    private DbmanagerBean dbmanager;
    @ManagedProperty(value = "#{authBean}")
    private AuthBean authBean;
    private int category_id;
    private String category_name;
    private String query;
    private List<Auction> result0;//variabile in base alle query
    private List<Auction> result1;//variabile in base alle query
    private List<Auction> result2;//variabile in base alle query
    private List<Auction> result3;//variabile in base alle query
    private List<Category> categories;//fisso
    private double taxes_to_pay = 0, auctions_to_pay = 0, deliveries_to_pay = 0, earn_from_auctions = 0;
    private List<Sell> sell_result;

    @PostConstruct
    public void init() throws SQLException {
        //if (conversation.isTransient()) {
        //    conversation.begin();
        //}
        setCategories(dbmanager.getCategories());
    }

    /*
     public void end()
     {
     conversation.end();
     }
     */
    /**
     * @param dbmanager the dbmanager to set
     */
    public void setDbmanager(DbmanagerBean dbmanager) {
        this.dbmanager = dbmanager;
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
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
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
     * @return the result
     */
    public List<Auction> getResult() {
        return result0;
    }

    /**
     * @param result the result to set
     */
    public void setResult(List<Auction> result) {
        this.result0 = result;
    }

    public String SubmitCategory() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        category_id = Integer.parseInt(paramMap.get("category_id"));
        category_name = paramMap.get("category_name");
        query = null;
        setResult(dbmanager.getAuctionByCategory(getCategory_id()));
        return "resultPage";
    }

    public String SubmitQuery() throws SQLException {
        category_name = null;
        setResult(dbmanager.getAuctionByQuery(getQuery()));
        return "resultPage";
    }

    public void latestResult() throws SQLException {
        query = null;
        category_name = null;
        setResult(dbmanager.getOnDueAuction());
    }

    public String goToMyAuctions() throws SQLException {
        if (authBean.getUser() != null) {
            setResult(dbmanager.getAuctionByUserId(authBean.getUser().getId()));
            setResult1(dbmanager.getAuctionByUserIdWithBids(authBean.getUser().getId()));
            setResult2(dbmanager.getAuctionByUserIdWithoutBids(authBean.getUser().getId()));
            setResult3(dbmanager.getAuctionClosedByUserId(authBean.getUser().getId()));
        }
        return "myAuctionPage";

        /*
         } else {
         FacesMessage fm = new FacesMessage("Bisogna effettuare il login");
         FacesContext.getCurrentInstance().addMessage("Errore", fm);
            
         return "forceLoginPage";
         }
         *///TODO fillare il result

    }

    public String goToMyBids() throws SQLException {
        if (authBean.getUser() != null) {
            setResult(dbmanager.getAuctionByBidderId(authBean.getUser().getId()));
            setResult1(dbmanager.getAuctionByBidderIdWinner(authBean.getUser().getId()));
            setResult2(dbmanager.getAuctionByBidderIdLoser(authBean.getUser().getId()));
            setResult3(dbmanager.getAuctionClosedByBidderId(authBean.getUser().getId()));
        }
        return "myBidsPage";
        /*
         } else {
         FacesMessage fm = new FacesMessage("Bisogna effettuare il login");
         FacesContext.getCurrentInstance().addMessage("Errore", fm);
         return "forceLoginPage";
         }*/
    }

    public String goToMySummary() throws SQLException {
        if (authBean.getUser() != null) {
            taxes_to_pay = 0;
            earn_from_auctions = 0;
            auctions_to_pay = 0;
            deliveries_to_pay = 0;

            sell_result = dbmanager.getSellBySeller_id(authBean.getUser().getId());
            for (Sell s : sell_result) {
                taxes_to_pay += s.getTax();
                earn_from_auctions += s.getFinal_price();
            }


            setResult(dbmanager.getAuctionClosedByBidderId(authBean.getUser().getId()));
            for (Auction a : result0) {
                auctions_to_pay += a.getActual_price();
                deliveries_to_pay += a.getDelivery_price();
            }



        }

        return "summaryPage";
    }

    /**
     * @return the categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * @param authBean the authBean to set
     */
    public void setAuthBean(AuthBean authBean) {
        this.authBean = authBean;
    }

    /**
     * @return the result1
     */
    public List<Auction> getResult1() {
        return result1;
    }

    /**
     * @param result1 the result1 to set
     */
    public void setResult1(List<Auction> result1) {
        this.result1 = result1;
    }

    /**
     * @return the result2
     */
    public List<Auction> getResult2() {
        return result2;
    }

    /**
     * @param result2 the result2 to set
     */
    public void setResult2(List<Auction> result2) {
        this.result2 = result2;
    }

    /**
     * @return the result3
     */
    public List<Auction> getResult3() {
        return result3;
    }

    /**
     * @param result3 the result3 to set
     */
    public void setResult3(List<Auction> result3) {
        this.result3 = result3;
    }

    /**
     * @return the taxes_to_pay
     */
    public double getTaxes_to_pay() {
        return taxes_to_pay;
    }

    /**
     * @param taxes_to_pay the taxes_to_pay to set
     */
    public void setTaxes_to_pay(double taxes_to_pay) {
        this.taxes_to_pay = taxes_to_pay;
    }

    /**
     * @return the auctions_to_pay
     */
    public double getAuctions_to_pay() {
        return auctions_to_pay;
    }

    /**
     * @param auctions_to_pay the auctions_to_pay to set
     */
    public void setAuctions_to_pay(double auctions_to_pay) {
        this.auctions_to_pay = auctions_to_pay;
    }

    /**
     * @return the earn_from_auctions
     */
    public double getEarn_from_auctions() {
        return earn_from_auctions;
    }

    /**
     * @param earn_from_auctions the earn_from_auctions to set
     */
    public void setEarn_from_auctions(double earn_from_auctions) {
        this.earn_from_auctions = earn_from_auctions;
    }

    /**
     * @return the sell_result
     */
    public List<Sell> getSell_result() {
        return sell_result;
    }

    /**
     * @param sell_result the sell_result to set
     */
    public void setSell_result(List<Sell> sell_result) {
        this.sell_result = sell_result;
    }
}
