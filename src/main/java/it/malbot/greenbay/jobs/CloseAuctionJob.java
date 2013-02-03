/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.jobs;

import it.malbot.greenbay.beans.DbmanagerBean;
import it.malbot.greenbay.beans.MailerBean;
import it.malbot.greenbay.model.Auction;
import it.malbot.greenbay.model.Sell;
import it.malbot.greenbay.model.User;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
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
        // Get bean from servlet context (same as application scope)  
        MailerBean mailer = (MailerBean) servletContext.getAttribute("mailer");
        System.out.println("ho il mailerbean ");



        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        System.out.println("ho pure l'id dell'asta ");

        int auction_id = dataMap.getInt("auction_id");
        System.out.println("ovvero " + auction_id);
        Auction auction;
        Sell sell = new Sell();
        Double tax, price;
        try {
            System.out.println("mi chiamo " + dbmanager.findUsernameById(1));
            auction = dbmanager.findAuctionById(auction_id);

            System.out.println("Provo a chiudere l' asta " + auction.getDescription());
            dbmanager.closeAuction(auction_id);
            System.out.println("Ho chiuso asta " + auction.getDescription());

            //generazione di una vendita base con tasse 1.23
            sell.setSeller_id(auction.getUser_id());
            sell.setAuction_id(auction_id);
            sell.setFinal_price(0);
            sell.setTax(1.23);
            System.out.println("Generato i dati base di un asta ");

            //se c'è winner
            if (auction.getWinner_id() != 0) {

                //prelevo il prezzo finale
                price = auction.getActual_price();
                //calcolo le tasse
                
                tax = roundToHalf((price / 100) * 1.25);
                

                sell.setFinal_price(price);
                sell.setTax(tax);
                sell.setBuyer_id(auction.getWinner_id());
            }

            //inserisco il sell al db
            dbmanager.insertSell(sell);
            System.out.println("Inserito il sell correttamente ");


            User user = dbmanager.findUser(auction.getUser_id());
            User winner = dbmanager.findUser(auction.getWinner_id());

            try {
                //notifico al venditore
                mailer.SendMail(user.getMail(),
                        "Asta " + auction.getId() + " conclusa",
                        "L'asta " + auction.getDescription() + " si è conclusa.");

                if (winner != null) {
                    //notifico al winner se esiste
                    mailer.SendMail(winner.getMail(),
                            "Asta " + auction.getId() + " conclusa",
                            "L'asta " + auction.getDescription() + " si è conclusa, sei il vincitore!");
                }
            } catch (MessagingException ex) {
                Logger.getLogger(CloseAuctionJob.class.getName()).log(Level.SEVERE, null, ex);
            }




        } catch (SQLException ex) {
            System.out.println("non va dbmanager ");
            Logger.getLogger(CloseAuctionJob.class.getName()).log(Level.SEVERE, null, ex);
        }






    }

    public static double roundToHalf(double x) {
        return (double) (Math.ceil(x * 2) / 2);
    }
}
