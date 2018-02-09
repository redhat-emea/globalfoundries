package com.redhat.emea.globalfoundries;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class JMSProducerClient {
//    private static final Logger log = Logger.getLogger(JMSProducerClient.class.getName());

    // Set up all the default values
    private static final String DEFAULT_MESSAGE = "Message #";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/First";
    private static final String DEFAULT_MESSAGE_COUNT = "100";
    private static final String DEFAULT_USERNAME = "jmsuser";
    private static final String DEFAULT_PASSWORD = "jmspassword1!";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8180";

    public static void main(String[] args) {

        Context namingContext = null;

        try {
            String userName = System.getProperty("username", DEFAULT_USERNAME);
            String password = System.getProperty("password", DEFAULT_PASSWORD);

            // Set up the namingContext for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            env.put(Context.SECURITY_PRINCIPAL, userName);
            env.put(Context.SECURITY_CREDENTIALS, password);
            namingContext = new InitialContext(env);

            // Perform the JNDI lookups
            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            //log.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
            //log.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

            String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
            //log.info("Attempting to acquire destination \"" + destinationString + "\"");
            Destination destination = (Destination) namingContext.lookup(destinationString);
            //log.info("Found destination \"" + destinationString + "\" in JNDI");

            int count = Integer.parseInt(System.getProperty("message.count", DEFAULT_MESSAGE_COUNT));
            String content = System.getProperty("message.content", DEFAULT_MESSAGE);

            try (JMSContext context = connectionFactory.createContext(userName, password)) {
                //log.info("Sending " + count + " messages with content: " + content);
                String grouping = "_AMQ_GROUP_ID";
                //String grouping = "JMSXGroupID";
                for (int i = 1; i <= count; i++) {
                    if (i % 11 == 0) {
                        context.createProducer().setProperty(grouping, "mod11").send(destination, content + " mod11 " + (i/11));
                        System.out.println("Message [" + (i) + "] with " + grouping + "=mod11 and content=" + content + " mod11 " + (i/11));
                    } else if (i % 13 == 0) {
                        context.createProducer().setProperty(grouping, "mod13").send(destination, content + " mod13 " + (i/13));
                        System.out.println("Message [" + (i) + "] with " + grouping + "=mod13 and content=" + content + " mod13 " + (i/13));
                    } else if (i % 17 == 0) {
                        context.createProducer().setProperty(grouping, "mod17").send(destination, content + " mod17 " + (i/17));
                        System.out.println("Message [" + (i) + "] with " + grouping + "=mod17 and content=" + content + " mod17 " + (i/17));
                    } else {
                        context.createProducer().send(destination, content + " " + (i));
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (namingContext != null) {
                try {
                    namingContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                    //log.severe(e.getMessage());
                }
            }
        }
    }
}
