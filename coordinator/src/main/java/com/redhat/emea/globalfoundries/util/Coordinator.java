package com.redhat.emea.globalfoundries.util;

import com.redhat.emea.globalfoundries.ejb.Business;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
@TransactionAttribute(TransactionAttributeType.NEVER)
public class Coordinator {

    private final AtomicBoolean running = new AtomicBoolean(Boolean.FALSE);

    @EJB
    private Business business;

    private Thread runnerBean;

    public synchronized void stop() {
        System.out.println("Coordinator.stop");
        if (runnerBean != null && !runnerBean.isInterrupted()) {
            System.out.println("Coordinator.stop - Stopping RunnerBean");
            runnerBean.interrupt();
            runnerBean = null;
            System.out.println("Coordinator.stop - RunnerBean stopped");
        } else {
            System.out.println("Coordinator.stop - RunnerBean already stopped");
        }
        running.set(Boolean.FALSE);
    }

    public synchronized void start() throws EJBException {
        try {
            System.out.println("Coordinator.start");
            if (runnerBean == null && !isRunning()) {
                System.out.println("Coordinator.start - Starting RunnerBean");
                running.set(Boolean.TRUE);
                runnerBean = new RunnerBean("RunnerBean", business);
                System.out.println("runnerBean -> " + runnerBean);
                runnerBean.start();
                System.out.println("Coordinator.start - RunnerBean started");
            } else {
                System.out.println("Coordinator.start - RunnerBean already started");
            }
        } catch (Exception e) {
            System.err.println("Coordinator.EXCEPTION");
            System.err.println("runnerBean -> " + runnerBean);
            throw new IllegalStateException(e);
        }
    }

    public boolean isRunning() {
        return running.get();
    }

}
