package com.example.ToDoList_app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Acknowledgement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_state_transition_id", nullable = false)
    private TaskStateTransition taskStateTransition;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime timestamp;

    // Constructors, getters, and setters
    public Acknowledgement() {}

    public Acknowledgement(TaskStateTransition taskStateTransition, User user) {
        this.taskStateTransition = taskStateTransition;
        this.user = user;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskStateTransition getTaskStateTransition() {
        return taskStateTransition;
    }

    public void setTaskStateTransition(TaskStateTransition taskStateTransition) {
        this.taskStateTransition = taskStateTransition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
