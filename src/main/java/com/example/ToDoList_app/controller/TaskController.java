package com.example.ToDoList_app.controller;

import com.example.ToDoList_app.entity.Task;
import com.example.ToDoList_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @RequestMapping("/tasks/{id}")
    public Task getTaskByID(@PathVariable int id) {
        return taskService.getTaskByID(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tasks")
    public void addTask(@RequestBody Task task) {
        taskService.addTask(task);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tasks/{id}")
    public void updateTask(@PathVariable int id, @RequestBody Task task) {
        taskService.updateTask(id, task);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }
}
