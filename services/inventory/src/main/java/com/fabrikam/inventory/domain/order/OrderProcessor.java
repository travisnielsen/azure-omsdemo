package com.fabrikam.inventory.domain.order;

import java.io.IOException;

import javax.jms.JMSException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.qpid.jms.message.JmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor {

    private CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

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

        HttpGet httpGet = new HttpGet("https://www.google.com");
        int status;
        try (CloseableHttpResponse response = closeableHttpClient.execute(httpGet)) {
            status = response.getStatusLine().getStatusCode();
            logger.info("Received http dependency with status: " + String.valueOf(status));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }   
}