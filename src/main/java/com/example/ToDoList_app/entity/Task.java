package com.example.ToDoList_app.entity;

import jakarta.persistence.*;


@Entity
public class Task implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private State state;

    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructor
    public Task(int id, String title, String description, State state, Priority priority, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.priority = priority;
        this.user = user;
    }

    // No-argument constructor
    public Task() {}

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isToBeCompletedToday() {
        return state == State.TO_BE_COMPLETED_TODAY;
    }

    @Override
    public int compareTo(Task other) {
        int priorityComparison = other.priority.compareTo(this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        } else {
            return this.title.compareTo(other.title);
        }
    }
}
