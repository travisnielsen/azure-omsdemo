package com.fabrikam.inventory.domain.order;

import java.util.UUID;

import com.fabrikam.inventory.domain.DomainEvent;
import com.fabrikam.inventory.domain.EventType;

/**
 * An event that encapsulates a state transition for the {@link Order} domain object.
 *
 */
public class OrderEvent extends DomainEvent<Order, Integer> {

    private static final long serialVersionUID = -295422703255886286L;

    private Order subject;
    private EventType eventType;

    public OrderEvent() {
        this.setId(UUID.randomUUID().hashCode());
    }

    public OrderEvent(Order subject, EventType eventType) {
        this();
        this.subject = subject;
        this.eventType = eventType;
    }

    public OrderEvent(EventType orderCreated) {
        this.eventType = orderCreated;
    }

    @Override
    public Order getSubject() {
        return subject;
    }

    @Override
    public void setSubject(Order subject) {
        this.subject = subject;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "subject=" + subject +
                ", eventType=" + eventType +
                '}';
    }
}