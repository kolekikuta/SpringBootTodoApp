package org.example.springboottodoapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTaskDTO {
    private String task;
    private LocalDateTime dueDate;
}
