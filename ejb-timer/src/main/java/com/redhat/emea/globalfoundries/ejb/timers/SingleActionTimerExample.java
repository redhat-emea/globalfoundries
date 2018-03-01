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
public class SingleActionTimerExample {

    @Resource
    private TimerService timerService;

    @Timeout()
    public void scheduler(Timer timer) {
        System.out.println("SingleActionTimerExample - Doing my task...");
        try {
            Thread.sleep(12000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        schedule();
    }

    @PostConstruct
    private void initialize() {
        schedule();
        System.out.println("SingleActionTimerExample.initialized");
    }

    private void schedule() {
        timerService.createSingleActionTimer(10000l, new TimerConfig("SingleActionTimer duration.second(10)", true));
        System.out.println("SingleActionTimerExample.scheduled");
    }


    @PreDestroy
    private void stop() {
        System.out.println("SingleActionTimerExample - Stopping timers.");
        /*for (Timer timer : timerService.getTimers()) {
            System.out.println("SingleActionTimerExample - Stopping timer: " + timer.getInfo());
            timer.cancel();
        }*/
    }
}
