package com.fabrikam.orders.domain;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.EventTelemetry;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository repository;
    
    @Autowired
    private JmsTemplate jmsTemplate;

    private CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    private TelemetryClient telemetryClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private static final String DESTINATION_NAME = "orders";

    public OrderController(TelemetryClient client) {
        this.telemetryClient = client;
    }

    @GetMapping("/")
    public int root() {
        return 200;
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<Order> getOrder(@PathVariable String id) {

        Assert.state(id != null, "The orderId must not equal null");
        final Mono<Order> findByIdMono = repository.findById(id);
        final Order findByIdOrder = findByIdMono.block();
        return Optional.ofNullable(findByIdOrder);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String createOrder(@RequestBody Order order) throws Exception {
        
        // Cosmos DB
        final Mono<Order> saveOrderMono = repository.save(order);
        final Order savedOrder = saveOrderMono.block();

        // Service Bus
        try {
            jmsTemplate.convertAndSend(DESTINATION_NAME, new OrderEvent(savedOrder, EventType.ORDER_CREATED));
            LOGGER.info("submitted order to Service Bus");
        } catch (JmsException ex) {
            LOGGER.error("Error submitting message to Service Bus", ex);
            return "Error submitting message Order";
        }
        
        return "Added " + savedOrder.toString();
    }

    @GetMapping("/dependency")
    public int trackDependencyAutomatically() throws IOException {
        HttpGet httpGet = new HttpGet("https://www.google.com");
        int status;
        try (CloseableHttpResponse response = closeableHttpClient.execute(httpGet)) {
            status = response.getStatusLine().getStatusCode();
        }
        return status;
    }

    @GetMapping("/event")
    public String trackEvent() {
        EventTelemetry telemetry = new EventTelemetry("Some event occurred");
        telemetryClient.trackEvent(telemetry);
        return "Created EVENT item";
    }

    @GetMapping("/exception")
    public Integer trackException() throws Exception {
        Integer result = 1 / 0;
        return result;
    }

    @GetMapping("/logexception")
    public String trackHandledException() throws Exception {
        Exception e = new Exception("Test exception");
        telemetryClient.trackException(e);
        return "Exception message sent";
    }

    @GetMapping("/logtrace")
    public String trackTrace() {
        LOGGER.trace("test TRACE message");
        return "Created TRACE item";
    }

    @GetMapping("/loginfo")
    public String trackInfo() {
        LOGGER.info("test info message");
        return "Created INFO item";
    }
    
    @GetMapping("/logwarn")
    public String trackWarn() {
        LOGGER.warn("test warning message");
        return "Created WARN item";
    }

    @GetMapping("/logerror")
    public String trackError() {
        LOGGER.error("test error message", new Exception("test error exception"));
        return "Created ERROR item";
    }
    
}