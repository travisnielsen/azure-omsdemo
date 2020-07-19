package com.fabrikam.orders.domain;

import java.io.Serializable;

/**
 * A domain event is an abstract class that describes a behavior within a domain.
 *
 * @param <T>  is the type of domain object that this event applies to.
 * @param <ID> is the type of identity for the domain event.
 * @author Kenny Bastani
 */
public abstract class DomainEvent<T, ID> implements Serializable {

    private ID id;
    private Long createdAt;
    private Long lastModified;
    private String traceId;

    public DomainEvent() { }

    public ID getId() { return id; }
    public void setId(ID id) { this.id = id; }

    public String getTraceId() { return traceId; }
    public void setTraceId(String operationId) { this.traceId = operationId; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public Long getLastModified() { return lastModified; }
    public void setLastModified(Long lastModified) { this.lastModified = lastModified; }

    public abstract T getSubject();
    public abstract void setSubject(T subject);

    public abstract EventType getEventType();
    public abstract void setEventType(EventType eventType);

    @Override
    public String toString() {
        return "DomainEvent {" +
                "id=" + id +
                "traceId=" + traceId +
                ", createdAt=" + createdAt +
                ", lastModified=" + lastModified +
                '}';
    }
}