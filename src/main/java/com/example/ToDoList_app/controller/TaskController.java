package com.example.ToDoList_app.controller;

import com.example.ToDoList_app.entity.Priority;
import com.example.ToDoList_app.entity.Task;
import com.example.ToDoList_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping(value = "/tasks")
    public void addTask(@RequestBody Task task) {
        taskService.addTask(task);
    }

    @PutMapping(value = "/tasks/{id}")
    public void updateTask(@PathVariable int id, @RequestBody Task task) {
        taskService.updateTask(id, task);
    }

    @DeleteMapping(value = "/tasks/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }

    @GetMapping("tasks/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable Priority priority) {
        return taskService.getTasksByPriority(priority);
    }

    @GetMapping("tasks/sorted")
    public List<Task> getSortedTasksByPriority() {
        return taskService.getSortedTasksByPriority();
    }

    @GetMapping("tasks/incomplete")
    public List<Task> getIncompleteTasks() {
        return taskService.getIncompleteTasks();
    }
}
