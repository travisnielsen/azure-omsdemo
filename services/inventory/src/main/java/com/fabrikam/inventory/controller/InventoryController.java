package com.fabrikam.inventory.controller;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.EventTelemetry;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import com.microsoft.applicationinsights.telemetry.Telemetry;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class InventoryController {

    private CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    private TelemetryClient telemetryClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(TelemetryClient client) {
        this.telemetryClient = client;
    }

    @GetMapping("/")
    public int root() {
        return 200;
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
