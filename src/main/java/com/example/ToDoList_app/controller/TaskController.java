package com.example.ToDoList_app.controller;

import com.example.ToDoList_app.entity.Priority;
import com.example.ToDoList_app.entity.State;
import com.example.ToDoList_app.entity.Task;
import com.example.ToDoList_app.entity.TaskStateTransition;
import com.example.ToDoList_app.entity.User;
import com.example.ToDoList_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return ResponseEntity.ok("Task added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task) {
        taskService.updateTask(id, task);
        return ResponseEntity.ok("Task updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Priority priority) {
        return ResponseEntity.ok(taskService.getTasksByPriority(priority));
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Task>> getSortedTasksByPriority() {
        return ResponseEntity.ok(taskService.getSortedTasksByPriority());
    }

    @GetMapping("/incomplete")
    public ResponseEntity<List<Task>> getIncompleteTasks() {
        return ResponseEntity.ok(taskService.getIncompleteTasks());
    }

    @GetMapping("/today")
    public ResponseEntity<List<Task>> getAllTasksToBeCompletedToday() {
        return ResponseEntity.ok(taskService.getAllTasksToBeCompletedToday());
    }

    @GetMapping("/accountable")
    public ResponseEntity<Map<User, List<Task>>> getTasksOfAccountableUsers() {
        return ResponseEntity.ok(taskService.getTasksOfAccountableUsers());
    }

    @PostMapping("/{id}/logTransition")
    public ResponseEntity<?> logTaskStateTransition(@PathVariable Long id, @RequestParam State previousState, @RequestParam State newState) {
        Task task = taskService.getTaskById(id);
        taskService.logTaskStateTransition(task, previousState, newState);
        return ResponseEntity.ok("Task state transition logged successfully");
    }

    @GetMapping("/{id}/latestTransition")
    public ResponseEntity<TaskStateTransition> getLatestTransition(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(taskService.getLatestTransition(task));
    }

    @GetMapping("/transitions/{transitionId}/acknowledgements")
    public ResponseEntity<Boolean> allUsersAcknowledged(@PathVariable Long transitionId) {
        TaskStateTransition transition = taskService.getTaskStateTransitionById(transitionId);
        return ResponseEntity.ok(taskService.allUsersAcknowledged(transition));
    }
}
