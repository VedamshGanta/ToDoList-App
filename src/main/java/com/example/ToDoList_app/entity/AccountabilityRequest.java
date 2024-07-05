package com.example.ToDoList_app.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AccountabilityRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(name = "requested_id", nullable = false)
    private User requested;

    private LocalDateTime timestamp;
    private RequestStatus status;

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    // Constructors, getters, and setters
    public AccountabilityRequest() {
        this.timestamp = LocalDateTime.now();
        this.status = RequestStatus.PENDING;
    }

    public AccountabilityRequest(User requester, User requested) {
        this.requester = requester;
        this.requested = requested;
        this.timestamp = LocalDateTime.now();
        this.status = RequestStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getRequested() {
        return requested;
    }

    public void setRequested(User requested) {
        this.requested = requested;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
