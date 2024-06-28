package com.example.ToDoList_app.repository;

import com.example.ToDoList_app.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
