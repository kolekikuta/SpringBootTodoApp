package org.example.springboottodoapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task description is required.")
    private String task;

    // False by default if not provided
    private boolean completed = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    //private LocalDateTime completedAt;
    private LocalDateTime dueDate;


    public Task(String task, boolean completed) {
        this.task = task;
        this.completed = completed;
    }


    public Task(String task, boolean completed, LocalDateTime dueDate) {
        this.task = task;
        this.completed = completed;
        this.dueDate = dueDate;
    }

    @AssertTrue(message = "Due date must be after created date.")
    public boolean isDueDateValid() {
        if (dueDate == null) return true;
        return dueDate.isAfter(createdAt);
    }
}
