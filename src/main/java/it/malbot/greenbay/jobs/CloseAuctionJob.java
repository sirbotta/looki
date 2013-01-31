/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.jobs;

import it.malbot.greenbay.beans.DbmanagerBean;
import it.malbot.greenbay.model.Auction;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CloseAuctionJob implements Job, Serializable {

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        System.out.println("sono partito ");
        //retrive servletContext
        ServletContext servletContext = (ServletContext) context.getMergedJobDataMap().get("servletContext");
        System.out.println("ho recuperato il context ");
        // Get bean from servlet context (same as application scope)  
        DbmanagerBean dbmanager = (DbmanagerBean) servletContext.getAttribute("dbmanager");
        System.out.println("ho il dbmanager ");



        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        System.out.println("ho pure l'id dell'asta ");

        int auction_id = dataMap.getInt("auction_id");
        System.out.println("ovvero " + auction_id);
        Auction auction;
        try {
            System.out.println("mi chiamo "+ dbmanager.findUsernameById(1));
            auction = dbmanager.findAuctionById(auction_id);
            System.out.println("Chiudo asta " + auction.getDescription());
        } catch (SQLException ex) {
            System.out.println("non va dbmanager ");
            Logger.getLogger(CloseAuctionJob.class.getName()).log(Level.SEVERE, null, ex);
        }






    }
}
