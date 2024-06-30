package com.example.ToDoList_app.service;

import com.example.ToDoList_app.entity.Priority;
import com.example.ToDoList_app.entity.Task;
import com.example.ToDoList_app.entity.User;
import com.example.ToDoList_app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TreeSet<Task> tasks;

    @Autowired
    private TaskRepository taskRepository;

    public TaskService() {
        this.tasks = new TreeSet<>((task1, task2) -> {
            int priorityComparison = task2.getPriority().compareTo(task1.getPriority());
            if (priorityComparison != 0) {
                return priorityComparison;
            } else {
                return task1.getTitle().compareTo(task2.getTitle());
            }
        });
    }

    public void init(User user) {
        List<Task> taskList = (List<Task>) taskRepository.findByUserId(user.getId());
        tasks.addAll(taskList);
    }

    @PreDestroy
    public void saveTasks() {
        taskRepository.saveAll(tasks);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void updateTask(int id, Task updatedTask) {
        tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .ifPresent(task -> {
                    tasks.remove(task);
                    tasks.add(updatedTask);
                });
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return tasks.stream()
                .filter(task -> task.getPriority().compareTo(priority) == 0)
                .collect(Collectors.toList());
    }

    public List<Task> getSortedTasksByPriority() {
        return tasks.stream()
                .sorted((task1, task2) -> {
                    int priorityComparison = task2.getPriority().compareTo(task1.getPriority());
                    if (priorityComparison != 0) {
                        return priorityComparison;
                    } else {
                        return task1.getTitle().compareTo(task2.getTitle());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Task> getIncompleteTasks() {
        return tasks.stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }
}
