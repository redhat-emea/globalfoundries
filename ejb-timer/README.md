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

CLI commands
------------

```
module add --name=jdbc.mysql --slot=main --resources=/opt/rh/mysql-connector-java-5.1.45-bin.jar --dependencies=javax.api,javax.transaction.api
connect 127.0.0.1:10090
batch
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=jdbc.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)
/subsystem=datasources/data-source=EJBTIMERS_DS:add(driver-name=mysql,min-pool-size=5,max-pool-size=50,jndi-name="java:/EJBTIMERS_DS",background-validation=true,connection-url="jdbc:mysql://172.17.0.2:3306/EJBTIMERS?useSSL=false",enabled=true,pool-prefill=true,user-name=root,password=root,statistics-enabled=true,jta=true,check-valid-connection-sql="SELECT 1")
/subsystem=ejb3/service=timer-service/database-data-store=ejbtimers-data-store:add(datasource-jndi-name=EJBTIMERS_DS)
/subsystem=ejb3/service=timer-service:write-attribute(name=default-data-store,value=ejbtimers-data-store)
run-batch
:shutdown(restart=true)
connect 127.0.0.1:10190
batch
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=jdbc.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)
/subsystem=datasources/data-source=EJBTIMERS_DS:add(driver-name=mysql,min-pool-size=5,max-pool-size=50,jndi-name="java:/EJBTIMERS_DS",background-validation=true,connection-url="jdbc:mysql://172.17.0.2:3306/EJBTIMERS?useSSL=false",enabled=true,pool-prefill=true,user-name=root,password=root,statistics-enabled=true,jta=true,check-valid-connection-sql="SELECT 1")
/subsystem=ejb3/service=timer-service/database-data-store=ejbtimers-data-store:add(datasource-jndi-name=EJBTIMERS_DS)
/subsystem=ejb3/service=timer-service:write-attribute(name=default-data-store,value=ejbtimers-data-store)
run-batch
:shutdown(restart=true)

/subsystem=messaging-activemq/server=default/ha-policy=shared-store-master:add(failover-on-server-shutdown=true)
/subsystem=messaging-activemq/server=default:write-attribute(name=cluster-user,value=clusteruser)
:shutdown(restart=true)
connect 127.0.0.1:10190
/subsystem=messaging-activemq/server=default:write-attribute(name=cluster-user,value=clusteruser)
/subsystem=messaging-activemq/server=default/ha-policy=shared-store-slave:add(failover-on-server-shutdown=true)
:shutdown(restart=true)
/subsystem=messaging-activemq/server=default/ha-policy=shared-store-slave:write-attribute(name=allow-failback,value=false)
:shutdown(restart=true)
connect 127.0.0.1:10090
:shutdown(restart=true)
exit
connect 127.0.0.1:10090
/subsystem=messaging-activemq/server=default:write-attribute(name=statistics-enabled,value=true)
connect 127.0.0.1:10190
/subsystem=messaging-activemq/server=default:write-attribute(name=statistics-enabled,value=true)
```


