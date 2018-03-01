package com.redhat.emea.globalfoundries.ejb.timers;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Singleton
public class SchedulerBean implements Scheduler {

    private static Logger LOGGER = Logger.getLogger(SchedulerBean.class.toString());

    @Resource
    private TimerService timerService;

    @Timeout
    public void scheduler(Timer timer) {
        LOGGER.info("HASingletonTimer");
        LOGGER.info("\tInfo=" + timer.getInfo());
        LOGGER.info("\tNext Timeout=" + timer.getNextTimeout());
        LOGGER.info("\tisCalendarTimer=" + timer.isCalendarTimer());
        LOGGER.info("\tisPersistent=" + timer.isPersistent());
    }

    @Override
    public void initialize(String info) {
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        // set schedule to every 10 seconds for demonstration
        scheduleExpression.hour("*").minute("*").second("0/10");
        // persistent must be false because the timer is started by the HASingleton service
        timerService.createCalendarTimer(scheduleExpression, new TimerConfig(info, false));
    }

    @Override
    public void stop() {
        LOGGER.info("Stop all existing HASingleton timers");
        for (Timer timer : timerService.getTimers()) {
            LOGGER.fine("Stop HASingleton timer: " + timer.getInfo());
            timer.cancel();
        }
    }
}
