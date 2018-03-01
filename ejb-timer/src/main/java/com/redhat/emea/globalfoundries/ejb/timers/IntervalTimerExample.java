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
public class IntervalTimerExample {

    @Resource
    private TimerService timerService;

    @Timeout()
    public void scheduler(Timer timer) {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        System.out.println("IntervalTimerExample.scheduler() " + timer.getInfo() + simpleDateFormat.format(currentTime));
    }

    @PostConstruct
    private void initialize() {
        schedule();
        System.out.println("IntervalTimerExample.initialized");
    }

    private void schedule() {
        timerService.createIntervalTimer(0l,10000l, new TimerConfig("IntervalTimer intervalDuration.second(10)", true));
        System.out.println("IntervalTimerExample.scheduled");
    }

    @PreDestroy
    private void stop() {
        System.out.println("IntervalTimerExample - Stopping timers.");
        /*for (Timer timer : timerService.getTimers()) {
            System.out.println("IntervalTimerExample - Stopping timer: " + timer.getInfo());
            timer.cancel();
        }*/
    }
}
