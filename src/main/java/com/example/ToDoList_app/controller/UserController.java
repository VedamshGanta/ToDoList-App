package com.example.ToDoList_app.controller;

import com.example.ToDoList_app.entity.AccountabilityRequest;
import com.example.ToDoList_app.entity.User;
import com.example.ToDoList_app.service.AccountabilityRequestService;
import com.example.ToDoList_app.service.TaskService;
import com.example.ToDoList_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AccountabilityRequestService accountabilityRequestService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            taskService.init(registeredUser); // Initialize tasks for the registered user
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict if email is already taken
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        try {
            User loggedInUser = userService.loginUser(loginRequest.get("email"), loginRequest.get("password"));
            taskService.init(loggedInUser); // Initialize tasks for the logged-in user
            return ResponseEntity.ok(loggedInUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 400 Bad Request if login fails
        }
    }

    @PostMapping("/accountability/request")
    public ResponseEntity<?> sendAccountabilityRequest(@RequestParam Long requesterId, @RequestParam Long requestedId) {
        try {
            AccountabilityRequest request = accountabilityRequestService.sendRequest(requesterId, requestedId);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 400 Bad Request if sending request fails
        }
    }

    @GetMapping("/accountability/requests/{requestedId}")
    public ResponseEntity<?> getAccountabilityRequests(@PathVariable Long requestedId) {
        try {
            List<AccountabilityRequest> requests = accountabilityRequestService.getRequestsForUser(requestedId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 400 Bad Request if retrieving requests fails
        }
    }

    @PostMapping("/accountability/respond")
    public ResponseEntity<?> respondToAccountabilityRequest(@RequestParam Long requestId, @RequestParam AccountabilityRequest.RequestStatus status) {
        try {
            accountabilityRequestService.respondToRequest(requestId, status);
            return ResponseEntity.ok("Response recorded");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 400 Bad Request if responding fails
        }
    }
}
