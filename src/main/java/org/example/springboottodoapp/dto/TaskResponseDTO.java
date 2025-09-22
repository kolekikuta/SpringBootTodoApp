package org.example.springboottodoapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
    private String task;
    private boolean completed;
    private LocalDateTime dueDate;
}
