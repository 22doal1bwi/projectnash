package de.projectnash.application.util;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;


/**
 * This class starts up with the application server and provides a scheduler for {@link CertificateExpireDbScheduler}.
 * @author Silvio D'Alessandro
 *
 */
public class StartUpSchedulerServlet extends HttpServlet{

	private static final long serialVersionUID = 4473549060699664050L;

	/**
	 * Initializes the scheduler on server startup.
	 */
	public void init() throws ServletException {
		
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	    Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			scheduler.start();
			
    	    @SuppressWarnings("deprecation")
			JobDetail certificateExpireJob = new JobDetailImpl("CertificateExpireJob", Scheduler.DEFAULT_GROUP, CertificateExpireDbScheduler.class);
    	    
    	    /** Only for test purposes every 5 minutes. */
    	    @SuppressWarnings("deprecation")
			SimpleTrigger everyDayCheckTrigger = new SimpleTriggerImpl("CheckDbEveryDay", Scheduler.DEFAULT_GROUP, new Date(),
    		                  null, SimpleTrigger.REPEAT_INDEFINITELY, 300000);
    	    
    	    scheduler.scheduleJob(certificateExpireJob, everyDayCheckTrigger);  
    	   
		} catch (SchedulerException e) {
			e.printStackTrace();
		}    
	}
}
