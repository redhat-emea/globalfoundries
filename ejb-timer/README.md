ejb-timer: Example of EJB Timer Service - @Schedule and @Timeout
===========================================

The `ejb-timer` quickstart demonstrates how to use the EJB timer service in Red Hat JBoss Enterprise Application Platform. This example creates a timer service that uses the `@Schedule` and `@Timeout` annotations. 


The following EJB Timer services are demonstrated:

 * `@Schedule`: Uses this annotation to mark a method to be executed according to the calendar schedule specified in the attributes of the annotation. This example schedules a message to be printed to the server console every 6 seconds.
 * `@Timeout`: Uses this annotation to mark a method to execute when a programmatic timer goes off. This example sets the timer to go off every 3 seconds, at which point the method prints a message to the server console.
 

Build and Deploy the Quickstart
-------------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command prompt and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean install wildfly:deploy

This will deploy `target/jboss-ejb-timer.war` to the running instance of the server.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command prompt and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn wildfly:undeploy

