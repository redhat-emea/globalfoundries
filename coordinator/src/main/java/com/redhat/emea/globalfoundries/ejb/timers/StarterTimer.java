package com.redhat.emea.globalfoundries.ejb.timers;

import com.redhat.emea.globalfoundries.util.Coordinator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;

@Singleton()
@Startup()
@TransactionAttribute(TransactionAttributeType.NEVER)
public class StarterTimer {

    @Resource
    private TimerService timerService;

    @EJB
    private Coordinator coordinator;

    @Resource
    private EJBContext ejbContext;

    @Timeout()
    public void scheduler(Timer timer) {
        try {
            coordinator.start();
        } catch (EJBException ee) {
            System.err.println("StarterTimer.ROLLBACK");
        }
    }

    @PostConstruct
    private void initialize() {
        schedule();
        System.out.println("StarterTimer.initialized");
    }

    private void schedule() {
        if (timerService.getTimers().isEmpty()) {
            timerService.createIntervalTimer(0l, 2000l, new TimerConfig("Start the EJB now and every 2 seconds", true));
        }
        System.out.println("StarterTimer.scheduled");
    }

    //@PreDestroy
    private void stop() {
        System.out.println("StarterTimer - Stopping timers.");
        coordinator.stop();
        for (Timer timer : timerService.getTimers()) {
            System.out.println("StarterTimer - Stopping timer: " + timer.getInfo());
            timer.cancel();
        }
    }
}
