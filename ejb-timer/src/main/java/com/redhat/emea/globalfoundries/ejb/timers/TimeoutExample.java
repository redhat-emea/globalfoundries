package com.redhat.emea.globalfoundries.ejb.timers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.interceptor.InvocationContext;

@Singleton()
@Startup()
public class TimeoutExample {

    @Resource
    private TimerService timerService;

    @Timeout()
    public void scheduler(Timer timer) {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        System.out.println("TimeoutExample.scheduler() " + timer.getInfo() + simpleDateFormat.format(currentTime));
    }

    @PostConstruct
    public void initialize(InvocationContext ctx) {
        ScheduleExpression se = new ScheduleExpression();
        // Set schedule to every 3 seconds (starting at second 0 of every minute).
        se.hour("*").minute("*").second("*/10");
        timerService.createCalendarTimer(se, new TimerConfig("EJB timer service timeout at ", true));
    }

    @PreDestroy
    public void stop() {
        System.out.println("EJB Timer: Stop timers.");
        for (Timer timer : timerService.getTimers()) {
            System.out.println("Stopping timer: " + timer.getInfo());
            timer.cancel();
        }
    }
}
