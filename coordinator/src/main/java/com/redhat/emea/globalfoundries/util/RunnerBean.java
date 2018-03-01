package com.redhat.emea.globalfoundries.util;

import com.redhat.emea.globalfoundries.ejb.Business;

public class RunnerBean extends Thread {

    private Business business;

    public RunnerBean(String name, Business business) {
        super(name);
        this.business = business;
    }

    @Override
    public synchronized void run() {
        super.run();
        try {
            this.business.doTask();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
