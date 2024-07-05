package com.example.ToDoList_app.service;

import com.example.ToDoList_app.entity.AccountabilityRequest;
import com.example.ToDoList_app.entity.User;
import com.example.ToDoList_app.repository.AccountabilityRequestRepository;
import com.example.ToDoList_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountabilityRequestService {

    @Autowired
    private AccountabilityRequestRepository accountabilityRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public AccountabilityRequest sendRequest(Long requesterId, Long requestedId) {
        User requester = userRepository.findById(requesterId).orElseThrow(() -> new IllegalArgumentException("Requester not found"));
        User requested = userRepository.findById(requestedId).orElseThrow(() -> new IllegalArgumentException("Requested user not found"));

        AccountabilityRequest request = new AccountabilityRequest(requester, requested);
        return accountabilityRequestRepository.save(request);
    }

    public List<AccountabilityRequest> getRequestsForUser(Long requestedId) {
        User requested = userRepository.findById(requestedId).orElseThrow(() -> new IllegalArgumentException("Requested user not found"));
        return accountabilityRequestRepository.findByRequested(requested);
    }

    public void respondToRequest(Long requestId, AccountabilityRequest.RequestStatus status) {
        AccountabilityRequest request = accountabilityRequestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Request not found"));
        request.setStatus(status);
        accountabilityRequestRepository.save(request);
    }
}
