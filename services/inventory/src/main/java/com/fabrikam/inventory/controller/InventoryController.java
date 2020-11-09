package com.fabrikam.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class InventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    @GetMapping("/")
    public int root() {
        LOGGER.trace("Inventory service called at root path");
        return 200;
    }

}
