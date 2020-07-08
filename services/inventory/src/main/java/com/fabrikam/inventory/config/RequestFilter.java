package com.fabrikam.inventory.config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.applicationinsights.extensibility.TelemetryProcessor;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.telemetry.Telemetry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestFilter implements TelemetryProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    private List<String> exempturls = new ArrayList<>();

    // private String exempturls;
    // public String getUrls() { return this.exempturls; }

    public RequestFilter() {
        this.exempturls.add("probe");
        this.exempturls.add("favicon.ico");
        LOGGER.info("Constructor: Added excluded paths from telemetry: 'probe and /favicon.ico' ");
    }

    /*
     * This method is called for each item of telemetry to be sent. Return false to
     * discard it. Return true to allow other processors to inspect it.
     */
    @Override
    public boolean process(Telemetry telemetry) {

        if (telemetry == null) {
            return true;
        }
        if (!(telemetry instanceof RequestTelemetry)) {
            return true;
        }

        RequestTelemetry requestTelemetry = (RequestTelemetry) telemetry;
        URI uri = null;

        try {
            uri = requestTelemetry.getUrl().toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            LOGGER.trace("Invalid URL or URI: " + e.getMessage());
            return false;   // do not track malformed data in telemetry
        }
        if (uri == null) { return true; }
        else {
            
            // Always exempt the root path from telemetry
            if (uri.getPath().equals("/")) { 
                return false;
            }

            for (String notNeededUri : exempturls) {
                if (uri.getPath().contains(notNeededUri)) {
                    return false;
                }
            }
        }
        return true;
    }
}