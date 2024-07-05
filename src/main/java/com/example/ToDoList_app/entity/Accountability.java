package com.example.ToDoList_app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "accountability", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "accountable_to_id"})
})
public class Accountability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "accountable_to_id", nullable = false)
    private User accountableTo;

    // Constructors, getters, and setters
    public Accountability() {}

    public Accountability(User user, User accountableTo) {
        this.user = user;
        this.accountableTo = accountableTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAccountableTo() {
        return accountableTo;
    }

    public void setAccountableTo(User accountableTo) {
        this.accountableTo = accountableTo;
    }
}
