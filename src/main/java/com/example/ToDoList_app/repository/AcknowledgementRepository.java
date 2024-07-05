package com.example.ToDoList_app.repository;

import com.example.ToDoList_app.entity.Acknowledgement;
import com.example.ToDoList_app.entity.TaskStateTransition;
import com.example.ToDoList_app.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AcknowledgementRepository extends CrudRepository<Acknowledgement, Long> {
    List<Acknowledgement> findByTaskStateTransition(TaskStateTransition taskStateTransition);
}
