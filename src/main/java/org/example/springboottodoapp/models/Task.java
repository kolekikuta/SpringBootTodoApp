package org.example.springboottodoapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

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

    public Task(String task, boolean completed) {
        this.task = task;
        this.completed = completed;
    }
}
