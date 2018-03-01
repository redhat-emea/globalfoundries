package com.redhat.emea.globalfoundries.ejb.timers;

import org.jboss.msc.service.*;
import org.wildfly.clustering.singleton.SingletonServiceBuilderFactory;
import org.wildfly.clustering.singleton.SingletonServiceName;
import org.wildfly.clustering.singleton.election.NamePreference;
import org.wildfly.clustering.singleton.election.PreferredSingletonElectionPolicy;
import org.wildfly.clustering.singleton.election.SimpleSingletonElectionPolicy;

import java.util.logging.Logger;

public class HATimerServiceActivator implements ServiceActivator {
    private final Logger log = Logger.getLogger(this.getClass().toString());

    @Override
    public void activate(ServiceActivatorContext context) {
        log.info("HATimerService will be installed!");

        HATimerService service = new HATimerService();
        ServiceName factoryServiceName = SingletonServiceName.BUILDER.getServiceName("server", "default");
        ServiceController<?> factoryService = context.getServiceRegistry().getRequiredService(factoryServiceName);
        SingletonServiceBuilderFactory factory = (SingletonServiceBuilderFactory) factoryService.getValue();
        factory.createSingletonServiceBuilder(HATimerService.SINGLETON_SERVICE_NAME, service)
            /*
             * The NamePreference is a combination of the node name (-Djboss.node.name) and the name of
             * the configured cache "singleton". If there is more than 1 node, it is possible to add more than
             * one name and the election will use the first available node in that list.
             *   -  To pass a chain of election policies to the singleton and tell JGroups to run the
             * singleton on a node with a particular name, uncomment the first line  and
             * comment the second line below.
             *   - To pass a list of more than one node, comment the first line and uncomment the
             * second line below.
             */
//            .electionPolicy(new PreferredSingletonElectionPolicy(new SimpleSingletonElectionPolicy(), new NamePreference("et1/singleton")))
            .electionPolicy(new PreferredSingletonElectionPolicy(new SimpleSingletonElectionPolicy(), new NamePreference("et1/singleton"), new NamePreference("node2/singleton")))

            .build(new DelegatingServiceContainer(context.getServiceTarget(), context.getServiceRegistry()))
            .setInitialMode(ServiceController.Mode.ACTIVE)
            .install();
    }
}
