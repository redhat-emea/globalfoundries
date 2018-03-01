package com.redhat.emea.globalfoundries.ejb.timers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.interceptor.InvocationContext;
import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton()
@Startup()
public class CalendarTimerExample {

    @Resource
    private TimerService timerService;

    @Timeout()
    public void scheduler(Timer timer) {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        System.out.println("CalendarTimerExample.scheduler() " + timer.getInfo() + simpleDateFormat.format(currentTime));
    }

    @PostConstruct
    private void initialize() {
        schedule();
        System.out.println("CalendarTimerExample.initialized");
    }

    private void schedule() {
        ScheduleExpression se = new ScheduleExpression();
        se.hour("*").minute("*").second("*/10");
        timerService.createCalendarTimer(se, new TimerConfig("CalendarTimer se.hour(*).minute(*).second(*/10)", true));
        System.out.println("CalendarTimerExample.scheduled");
    }

    @PreDestroy
    private void stop() {
        System.out.println("CalendarTimerExample - Stopping timers.");
        /*for (Timer timer : timerService.getTimers()) {
            System.out.println("CalendarTimerExample - Stopping timer: " + timer.getInfo());
            timer.cancel();
        }*/
    }

}
