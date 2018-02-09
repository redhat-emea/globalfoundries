package com.redhat.emea.globalfoundries;

public class MultipleJMSConsumerClient {

    public static void main(String[] args) {
        System.out.println("Multiple JMSConsumerClient");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("Starting JMSConsumerClient[" + (i + 1) + "]");
                (new Thread(new JMSConsumerClient(), "jmsConsumerClient-" + (i + 1))).start();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
