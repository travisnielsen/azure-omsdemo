package com.fabrikam.inventory.domain.order;

import javax.jms.JMSException;

import org.apache.qpid.jms.message.JmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor {

    private static final String TOPIC_NAME = "orders";
    private static final String SUBSCRIPTION_NAME = "inventoryService";
    private final Logger logger = LoggerFactory.getLogger(OrderProcessor.class);

    @JmsListener(destination = TOPIC_NAME, containerFactory = "topicJmsListenerContainerFactory", subscription = SUBSCRIPTION_NAME)
    public void receiveMessage(JmsMessage message) {

        String messageId = "unknown";

        try {
            messageId = message.getJMSMessageID();
            logger.info("Received message from Service Bus: {}", messageId);

            // TODO: Get the message body and process it
            // OrderEvent order = message.getBody(OrderEvent.class);
            // logger.info("order: {}", order.getSubject());
            
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }

    }   
}