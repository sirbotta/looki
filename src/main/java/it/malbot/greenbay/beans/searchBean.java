/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.Auction;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author simone
 */
/// per lo scope custom usare
//in creazione <f:actionListener type="it.malbot.greenbay.helper.CreateCustomScope" />
//in distruzione <f:actionListener type="it.malbot.greenbay.helper.DestroyCustomScope" />
@ManagedBean
//@CustomScoped("#{CUSTOM_SCOPE}")
@ConversationScoped
public class searchBean implements Serializable {

    /**
     * Creates a new instance of searchBean
     */
    @Inject
    private Conversation conversation;
    @ManagedProperty(value = "#{dbmanager}")
    private DbmanagerBean dbmanager;
    @ManagedProperty(value = "#{param.category_id}")
    private int category_id;
    @ManagedProperty(value = "#{param.category_name}")
    private String category_name;
    private String query;
    private List<Auction> result;

    
    
    @PostConstruct
    public void init()
    {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        
    }
    
    public void end()
    {
        conversation.end();
    }
    
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
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(List<Auction> result) {
        this.result = result;
    }

    public String SubmitCategory() throws SQLException {
        setResult(dbmanager.getAuctionByCategory(getCategory_id()));
        return "resultPage";
    }

    public String SubmitQuery() throws SQLException {  
        setResult(dbmanager.getAuctionByQuery(getQuery()));
        return "resultPage";
    }
    
}
