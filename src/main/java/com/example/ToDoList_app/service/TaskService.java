package com.example.ToDoList_app.service;

import com.example.ToDoList_app.entity.*;
import com.example.ToDoList_app.repository.AccountabilityRepository;
import com.example.ToDoList_app.repository.AcknowledgementRepository;
import com.example.ToDoList_app.repository.TaskRepository;
import com.example.ToDoList_app.repository.TaskStateTransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TreeSet<Task> tasks;
    private Map<User, List<Task>> accountableUserTasks;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AccountabilityRepository accountabilityRepository;

    @Autowired
    private TaskStateTransitionRepository taskStateTransitionRepository;

    @Autowired
    private AcknowledgementRepository acknowledgementRepository;

    public TaskService() {
        this.tasks = new TreeSet<>((task1, task2) -> {
            int priorityComparison = task2.getPriority().compareTo(task1.getPriority());
            if (priorityComparison != 0) {
                return priorityComparison;
            } else {
                return task1.getTitle().compareTo(task2.getTitle());
            }
        });
        this.accountableUserTasks = new HashMap<>();
    }

    public void init(User user) {
        List<Task> taskList = taskRepository.findByUserId(user.getId());
        tasks.addAll(taskList);

        List<Accountability> accountableUsers = accountabilityRepository.findByAccountableTo(user);
        for (Accountability accountability : accountableUsers) {
            List<Task> accountableUserTaskList = taskRepository.findByUserId(accountability.getUser().getId());
            accountableUserTasks.put(accountability.getUser(), new ArrayList<>(accountableUserTaskList));
        }
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

    public void updateTask(Long id, Task updatedTask) {
        tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .ifPresent(task -> {
                    tasks.remove(task);
                    tasks.add(updatedTask);
                });
    }

    public void deleteTask(Long id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return tasks.stream()
                .filter(task -> task.getPriority().compareTo(priority) == 0)
                .collect(Collectors.toList());
    }

    public List<Task> getSortedTasksByPriority() {
        return new ArrayList<>(tasks);
    }

    public List<Task> getIncompleteTasks() {
        return tasks.stream()
                .filter(task -> task.getState() != State.COMPLETED)
                .collect(Collectors.toList());
    }

    public List<Task> getAllTasksToBeCompletedToday() {
        return tasks.stream()
                .filter(Task::isToBeCompletedToday)
                .collect(Collectors.toList());
    }

    public Map<User, List<Task>> getTasksOfAccountableUsers() {
        return accountableUserTasks;
    }

    public void logTaskStateTransition(Task task, State previousState, State newState) {
        // Deactivate the current active transition if any
        TaskStateTransition activeTransition = taskStateTransitionRepository.findActiveTransitionByTask(task);
        if (activeTransition != null) {
            activeTransition.setStatus(TransitionStatus.INACTIVE);
            taskStateTransitionRepository.save(activeTransition);
        }

        // Log the new transition
        TaskStateTransition transition = new TaskStateTransition(task, previousState, newState);
        taskStateTransitionRepository.save(transition);
        notifyAccountableUsers(task, transition);
    }

    public void notifyAccountableUsers(Task task, TaskStateTransition transition) {
        List<Accountability> accountableUsers = accountabilityRepository.findByUser(task.getUser());
        for (Accountability accountability : accountableUsers) {
            Acknowledgement acknowledgement = new Acknowledgement(transition, accountability.getAccountableTo());
            acknowledgementRepository.save(acknowledgement);
        }
    }

    public TaskStateTransition getLatestTransition(Task task) {
        return taskStateTransitionRepository.findActiveTransitionByTask(task);
    }

    public boolean allUsersAcknowledged(TaskStateTransition transition) {
        List<Accountability> accountableUsers = accountabilityRepository.findByUser(transition.getTask().getUser());
        List<Acknowledgement> acknowledgements = acknowledgementRepository.findByTaskStateTransition(transition);
        boolean allAcknowledged = accountableUsers.size() == acknowledgements.size();
        if (allAcknowledged) {
            transition.setStatus(TransitionStatus.INACTIVE);
            taskStateTransitionRepository.save(transition);
        }

        return allAcknowledged;
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    public TaskStateTransition getTaskStateTransitionById(Long transitionId) {
        return taskStateTransitionRepository.findById(transitionId).orElseThrow(() -> new IllegalArgumentException("Task state transition not found"));
    }
}
