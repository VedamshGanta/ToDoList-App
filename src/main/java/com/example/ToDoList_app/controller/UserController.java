package com.example.ToDoList_app.controller;

import com.example.ToDoList_app.entity.User;
import com.example.ToDoList_app.service.TaskService;
import com.example.ToDoList_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping("user/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            taskService.init(registeredUser); // Initialize tasks for the registered user
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict if email is already taken
        }
    }

    @PostMapping("user/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        try {
            User loggedInUser = userService.loginUser(email, password);
            taskService.init(loggedInUser); // Initialize tasks for the logged-in user
            return ResponseEntity.ok(loggedInUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 400 Bad Request if login fails
        }
    }
}
