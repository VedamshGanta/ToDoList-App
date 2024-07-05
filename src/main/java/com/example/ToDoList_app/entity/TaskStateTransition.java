package com.example.ToDoList_app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TaskStateTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Enumerated(EnumType.STRING)
    private State previousState;

    @Enumerated(EnumType.STRING)
    private State newState;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private TransitionStatus status;

    // Constructors, getters, and setters
    public TaskStateTransition() {}

    public TaskStateTransition(Task task, State previousState, State newState) {
        this.task = task;
        this.previousState = previousState;
        this.newState = newState;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public State getNewState() {
        return newState;
    }

    public void setNewState(State newState) {
        this.newState = newState;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransitionStatus getStatus() {
        return status;
    }

    public void setStatus(TransitionStatus status) {
        this.status = status;
    }
}
