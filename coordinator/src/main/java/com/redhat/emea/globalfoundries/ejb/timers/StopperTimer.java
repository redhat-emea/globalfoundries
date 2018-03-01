package com.redhat.emea.globalfoundries.ejb.timers;

import com.redhat.emea.globalfoundries.util.Coordinator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;

@Singleton()
@Startup()
@TransactionAttribute(TransactionAttributeType.NEVER)
public class StopperTimer {

    @Resource
    private TimerService timerService;

    @EJB
    private Coordinator coordinator;

    @Timeout()
    public void scheduler(Timer timer) {
        coordinator.stop();
    }

    @PostConstruct
    private void initialize() {
        schedule();
        System.out.println("StopperTimer.initialized");
    }

    private void schedule() {
        if (timerService.getTimers().isEmpty()) {
            timerService.createIntervalTimer(30000l,30000l, new TimerConfig("Stop the EJB every 30 seconds", true));
        }
        System.out.println("StopperTimer.scheduled");
    }

    //@PreDestroy
    private void stop() {
        System.out.println("StopperTimer - Stopping timers.");
        coordinator.stop();
        for (Timer timer : timerService.getTimers()) {
            System.out.println("StopperTimer - Stopping timer: " + timer.getInfo());
            timer.cancel();
        }
    }

}
