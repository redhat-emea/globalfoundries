package com.redhat.emea.globalfoundries.ejb.timers;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

/**
 * <p>A service to start schedule-timer as HASingleton timer in a clustered environment.
 * The {@link HATimerServiceActivator} will ensure that the timer is initialized only once in a cluster.</p>
 * <p>
 * The initialized timers must not persistent because it will be automatically restarted in case of a server restart
 * and exists twice within the cluster.<br/>
 * As this approach is no designed to interact with remote clients it is not possible to trigger reconfigurations.
 * For this purpose it might be a solution to read the timer configuration from a datasource and create a scheduler
 * which checks this configuration and trigger the reconfiguration.
 * </p>
 *
 * @author <a href="mailto:wfink@redhat.com">Wolf-Dieter Fink</a>
 */
public class HATimerService implements Service<String> {
    private static final Logger LOGGER = Logger.getLogger(HATimerService.class.toString());
    public static final ServiceName SINGLETON_SERVICE_NAME = ServiceName.JBOSS.append("quickstart", "ha", "singleton", "timer");

    /**
     * A flag whether the service is started.
     */
    private final AtomicBoolean started = new AtomicBoolean(false);

    /**
     * @return the name of the server node
     */
    public String getValue() throws IllegalStateException, IllegalArgumentException {
        LOGGER.info(String.format("%s is %s at %s", HATimerService.class.getSimpleName(), (started.get() ? "started" : "not started"), System.getProperty("jboss.node.name")));
        return "";
    }

    public void start(StartContext arg0) throws StartException {
        if (!started.compareAndSet(false, true)) {
            throw new StartException("The service is still started!");
        }
        LOGGER.info("Start HASingleton timer service '" + this.getClass().getName() + "'");

        final String node = System.getProperty("jboss.node.name");
        try {
            InitialContext ic = new InitialContext();
            ((Scheduler) ic.lookup("global/sync-timer/SchedulerBean!com.redhat.emea.globalfoundries.ejb.timers.Scheduler"))
                .initialize("HASingleton timer @" + node + " " + new Date());
        } catch (NamingException e) {
            throw new StartException("Could not initialize timer", e);
        }
    }

    public void stop(StopContext arg0) {
        if (!started.compareAndSet(true, false)) {
            LOGGER.warning("The service '" + this.getClass().getName() + "' is not active!");
        } else {
            LOGGER.info("Stop HASingleton timer service '" + this.getClass().getName() + "'");
            try {
                InitialContext ic = new InitialContext();
                ((Scheduler) ic.lookup("global/sync-timer/SchedulerBean!com.redhat.emea.globalfoundries.ejb.timers.Scheduler")).stop();
            } catch (NamingException e) {
                LOGGER.info("Could not stop timer:" + e.getMessage());
            }
        }
    }
}
