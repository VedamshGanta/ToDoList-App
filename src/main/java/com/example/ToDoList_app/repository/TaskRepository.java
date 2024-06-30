package com.example.ToDoList_app.repository;

import com.example.ToDoList_app.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByUserId(int userId);
}
