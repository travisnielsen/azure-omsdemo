package com.fabrikam.inventory;

import com.fabrikam.inventory.config.RequestFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.microsoft.applicationinsights.extensibility.TelemetryProcessor;

@SpringBootApplication
public class InventoryService {

	public static void main(String[] args) {
		SpringApplication.run(InventoryService.class, args);
	}

	@Bean
	public TelemetryProcessor requestFilter() {
		return new RequestFilter();
	}

}
