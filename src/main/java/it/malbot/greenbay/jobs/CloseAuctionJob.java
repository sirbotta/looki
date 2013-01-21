/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.jobs;

import it.malbot.greenbay.beans.DbmanagerBean;
import it.malbot.greenbay.model.Auction;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CloseAuctionJob implements Job {

    

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        
        //retrive servletContext
        ServletContext servletContext = (ServletContext) context.getMergedJobDataMap().get("servletContext"); 
        // Get bean from servlet context (same as application scope)  
        DbmanagerBean dbmanager = (DbmanagerBean) servletContext.getAttribute("dbmanager");  
        
        
        
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        int auction_id = dataMap.getInt("auction_id");
        try {
            Auction auction = dbmanager.findAuctionById(auction_id);
            System.out.println("Chiudo asta " + auction.getDescription());
        } catch (SQLException ex) {
            Logger.getLogger(CloseAuctionJob.class.getName()).log(Level.SEVERE, null, ex);
        }

        

    }

    
}
