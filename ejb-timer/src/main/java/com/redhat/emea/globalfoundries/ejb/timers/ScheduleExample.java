package com.redhat.emea.globalfoundries.ejb.timers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

//@Singleton
public class ScheduleExample {

//    @Schedule(second = "*/10", minute = "*", hour = "*", persistent = true)
    public void doWork() {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        System.out.println("Running every 10 seconds - invoked at " + simpleDateFormat.format(currentTime));
    }

}
