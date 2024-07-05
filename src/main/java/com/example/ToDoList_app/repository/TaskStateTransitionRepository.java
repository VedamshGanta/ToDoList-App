package com.example.ToDoList_app.repository;

import com.example.ToDoList_app.entity.Task;
import com.example.ToDoList_app.entity.TaskStateTransition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskStateTransitionRepository extends CrudRepository<TaskStateTransition, Long> {
    @Query("SELECT t FROM TaskStateTransition t WHERE t.task = :task AND t.status = 'ACTIVE' ORDER BY t.timestamp DESC")
    TaskStateTransition findActiveTransitionByTask(@Param("task") Task task);
}
