package com.example.ToDoList_app.repository;

import com.example.ToDoList_app.entity.AccountabilityRequest;
import com.example.ToDoList_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountabilityRequestRepository extends JpaRepository<AccountabilityRequest, Long> {
    List<AccountabilityRequest> findByRequested(User requested);
}
