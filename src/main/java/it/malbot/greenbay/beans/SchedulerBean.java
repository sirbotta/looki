/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.jobs.CloseAuctionJob;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author simone
 */
@ManagedBean(name = "scheduler" , eager=true)
@ApplicationScoped
public class SchedulerBean implements Serializable{

    /**
     * Creates a new instance of SchedulerBean
     */
    private Scheduler scheduler;

    public SchedulerBean() throws SchedulerException {

        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();

        //Get QuartzInitializerListener 
        StdSchedulerFactory stdSchedulerFactory = (StdSchedulerFactory) servletContext
                .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);

        scheduler = stdSchedulerFactory.getScheduler();

    }

    public void closeAuctionAt() throws SchedulerException {
        
        //recupero il servletContext
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        //creo un wrapper da passare al job
        JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("servletContext", servletContext);
        
        
            
        // Define job instance //per ora generato a caso solo su acution 1
        JobDetail job = JobBuilder.newJob(CloseAuctionJob.class)
                .withIdentity("job1"+Math.random() * 1000, "group1")
                .usingJobData("auction_id", 1).usingJobData(jobDataMap)
                .build();

        //1 minuto in date da ora //solo per esempio
        Date oldDate = new Date(); // oldDate == current time
        final long minutesInMillis = 60L * 1000L;
        Date startDate = new Date(oldDate.getTime() + 
                        (1L * minutesInMillis));
        
        
        //trigger da usare correttamente in futuro
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1").startAt(startDate)
                .build();
        // Define a Trigger that will fire "now", and not repeat
        Trigger triggernow = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1").startNow()
                .build();

        // Schedule the job with the trigger 
        scheduler.scheduleJob(job, triggernow);
    }
    
    public void closeAuctionAt(Date due_date,int auction_id) throws SchedulerException {
        
        //recupero il servletContext
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        //creo un wrapper da passare al job
        JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("servletContext", servletContext);
        
        
            
        // Define job instance //per ora generato a caso solo su acution 1
        JobDetail job = JobBuilder.newJob(CloseAuctionJob.class)
                .withIdentity("closeAuction-"+auction_id, "group1")
                .usingJobData("auction_id", auction_id)
                .usingJobData(jobDataMap)
                .build();
        
        
        //trigger da usare correttamente in futuro
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(due_date)
                .build();
        

        // Schedule the job with the trigger 
        scheduler.scheduleJob(job, trigger);
    }
    
}
