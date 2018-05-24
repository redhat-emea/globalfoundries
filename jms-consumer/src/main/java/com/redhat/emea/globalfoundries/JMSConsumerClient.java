package com.redhat.emea.globalfoundries;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;
import java.util.Properties;

public class JMSConsumerClient implements Runnable {
    //private static final Logger log = Logger.getLogger(JMSConsumerClient.class.getName());

    // Set up all the default values
    private static final String DEFAULT_MESSAGE = "Hello, World!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/First";
    private static final String DEFAULT_MESSAGE_COUNT = "10";
    private static final String DEFAULT_USERNAME = "jmsuser";
    private static final String DEFAULT_PASSWORD = "jmspassword1!";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8180";

    public static void main(String[] args) {
        System.out.println("Single JMSConsumerClient");
        JMSConsumerClient client = new JMSConsumerClient();
        client.consume();
    }

    @Override
    public void run() {
        consume();
    }

    public void consume(){
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

            try (JMSContext context = connectionFactory.createContext(userName, password)) {
                // Create the JMS consumer
                JMSConsumer consumer = context.createConsumer(destination);
                // Then receive the same number of messages that were sent
                while (true){
                    //String text = consumer.receiveBody(String.class, 5000);
                    Message message = consumer.receive();
                    //String group = message.getStringProperty("JMSXGroupID");
                    String group = message.getStringProperty(org.apache.activemq.artemis.api.core.Message.HDR_GROUP_ID.toString());
                    String text = message.getBody(String.class);
                    Enumeration e = message.getPropertyNames();
                    while (e.hasMoreElements()) {
                        Object o = e.nextElement();
                        System.out.println(Thread.currentThread().getName() + " - Property name: " + (String)o + " | value: " + message.getObjectProperty((String)o));
                    }
                    System.out.println(Thread.currentThread().getName() + " - Received message with group '" + group + "' and content: '" + text + "'");
                    //log.info("Received message with content " + text);
                    //if (text == null || "".equals(text)) break;
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } catch (NamingException e) {
            e.printStackTrace();
            //log.severe(e.getMessage());
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
