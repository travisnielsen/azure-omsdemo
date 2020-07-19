package com.fabrikam.inventory.domain.order;

import java.io.Serializable;


public class Order implements Serializable {

    private static final long serialVersionUID = -5236138490613454974L;

    private String id;
    private String itemName;
    private Integer itemQuantity;
    private String customerId;


    public Order(String id, String itemname, Integer quantity, String customerId) {
        this.id = id;
        this.itemName = itemname;
        this.itemQuantity = quantity;
        this.customerId = customerId;
    }

    public Order() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer quantity) {
        this.itemQuantity = quantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customer) {
        this.customerId = customer;
    }

    @Override
    public String toString() {
        return String.format("%s %s, %s", itemName, itemQuantity, customerId);
    }

}