package org.example.springboottodoapp;

import org.example.springboottodoapp.repositories.TaskRepository;
import org.example.springboottodoapp.services.TaskService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class TestConfig {
    @Bean
    TaskService taskService() {
        return Mockito.mock(TaskService.class);
    }

    @Bean
    TaskRepository taskRepository() {
        return Mockito.mock(TaskRepository.class);
    }
}
