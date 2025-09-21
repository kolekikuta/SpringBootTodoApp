package org.example.springboottodoapp.services;

import org.example.springboottodoapp.models.Task;
import org.example.springboottodoapp.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.springboottodoapp.exceptions.TaskNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createNewTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> findAllCompletedTask() {
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllInCompleteTask() {
        return taskRepository.findByCompletedFalse();
    }

    public List<Task> findAllOverdueTask() {
        return taskRepository.findByDueDateBeforeAndCompletedFalse(LocalDateTime.now());
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    public Task updateTask(Task task) {
        Task existing = findTaskById(task.getId());
        existing.setTask(task.getTask());
        existing.setCompleted(task.isCompleted());
        return taskRepository.save(task);
    }
}
