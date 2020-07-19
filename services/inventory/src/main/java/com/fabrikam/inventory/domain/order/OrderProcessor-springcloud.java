/* UNABLE TO RUN CURRENTLY

package com.fabrikam.inventory.domain.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.azure.spring.integration.core.AzureHeaders;
import com.microsoft.azure.spring.integration.core.api.Checkpointer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;


@EnableBinding(Sink.class)
public class OrderProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProcessor.class);

    @StreamListener(Sink.INPUT)
    public void handleMessage(String message, @Header(AzureHeaders.CHECKPOINTER) Checkpointer checkpointer)  { 

        LOGGER.info(String.format("New message received: '%s'", message.toString()));
        checkpointer.success().handle((r, ex) -> {
            if (ex == null) {
                System.out.println(String.format("Message '%s' successfully checkpointed", message));
            }
            return null;
        });

    }

}

*/