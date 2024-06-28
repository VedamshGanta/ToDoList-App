package com.example.ToDoList_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Task implements Comparable<Task> {

    @Id
    private int id;
    private String title;
    private String description;
    private boolean isCompleted;
    private LocalDate dueDate;
    private Priority priority;

    // Constructor
    public Task(int id, String title, String description, LocalDate dueDate, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = false;
        this.dueDate = dueDate;
        this.priority = priority;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    // Method to mark task as completed
    public void completeTask() {
        this.isCompleted = true;
    }

    // Method to display task details
    public void displayTask() {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Priority: " + priority);
        System.out.println("Completed: " + (isCompleted ? "Yes" : "No"));
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
