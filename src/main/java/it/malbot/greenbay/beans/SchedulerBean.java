/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.jobs.CloseAuctionJob;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/**
 *
 * @author simone
 */
@ManagedBean(name = "scheduler")//, eager=true)
@ApplicationScoped
public class SchedulerBean implements Serializable {

    /**
     * Creates a new instance of SchedulerBean
     */
    private Scheduler scheduler;
    private List<QuartzJob> quartzJobList = new ArrayList<QuartzJob>();

    public SchedulerBean() throws SchedulerException {

        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();

        //Get QuartzInitializerListener 
        StdSchedulerFactory stdSchedulerFactory = (StdSchedulerFactory) servletContext
                .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);

        scheduler = stdSchedulerFactory.getScheduler();

    }
    // deprecato usato solo in test

    public void closeAuctionAt() throws SchedulerException {

        //recupero il servletContext
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        //creo un wrapper da passare al job
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("servletContext", servletContext);



        // Define job instance //per ora generato a caso solo su acution 1
        JobDetail job = JobBuilder.newJob(CloseAuctionJob.class)
                .withIdentity("job1" + Math.random() * 1000, "group1")
                .usingJobData("auction_id", 1).usingJobData(jobDataMap)
                .build();

        //1 minuto in date da ora //solo per esempio
        Date oldDate = new Date(); // oldDate == current time
        final long minutesInMillis = 60L * 1000L;
        Date startDate = new Date(oldDate.getTime()
                + (1L * minutesInMillis));


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

    public void closeAuctionAt(Date due_date, int auction_id) throws SchedulerException {

        //recupero il servletContext
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        //creo un wrapper da passare al job
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("servletContext", servletContext);



        // Define job instance //per ora generato a caso solo su acution 1
        JobDetail job = JobBuilder.newJob(CloseAuctionJob.class)
                .withIdentity("closeAuction-" + auction_id, "auctions")
                .usingJobData("auction_id", auction_id)
                .usingJobData(jobDataMap)
                .build();


        //trigger da usare correttamente in futuro
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-" + auction_id, "auctions")
                .startAt(due_date)
                .build();


        // Schedule the job with the trigger 
        scheduler.scheduleJob(job, trigger);
    }

    ////// parte sperimentale
    public void fill_list() throws SchedulerException {
        quartzJobList.clear();

        // loop jobs by group
        for (String groupName : scheduler.getJobGroupNames()) {

            // get jobkey
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher
                    .jobGroupEquals(groupName))) {

                String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();

                // get job's trigger
                List<Trigger> triggers = (List<Trigger>) scheduler
                        .getTriggersOfJob(jobKey);
                Date nextFireTime = triggers.get(0).getNextFireTime();

                getQuartzJobList().add(new QuartzJob(jobName, jobGroup, nextFireTime));

            }

        }
    }

    public boolean killAJob(int auction_id)
            throws SchedulerException {

        String jobName = "closeAuction-" + auction_id, jobGroup = "auctions";

        JobKey jobKey = new JobKey(jobName, jobGroup);

        return scheduler.deleteJob(jobKey);
       

    }

    /**
     * @return the quartzJobList
     */
    public List<QuartzJob> getQuartzJobList() {
        return quartzJobList;
    }

    /**
     * @param quartzJobList the quartzJobList to set
     */
    public void setQuartzJobList(List<QuartzJob> quartzJobList) {
        this.quartzJobList = quartzJobList;
    }

    public static class QuartzJob {

        private static final long serialVersionUID = 1L;
        String jobName;
        String jobGroup;
        Date nextFireTime;

        public QuartzJob(String jobName, String jobGroup, Date nextFireTime) {

            this.jobName = jobName;
            this.jobGroup = jobGroup;
            this.nextFireTime = nextFireTime;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getJobGroup() {
            return jobGroup;
        }

        public void setJobGroup(String jobGroup) {
            this.jobGroup = jobGroup;
        }

        public Date getNextFireTime() {
            return nextFireTime;
        }

        public void setNextFireTime(Date nextFireTime) {
            this.nextFireTime = nextFireTime;
        }
    }
}
