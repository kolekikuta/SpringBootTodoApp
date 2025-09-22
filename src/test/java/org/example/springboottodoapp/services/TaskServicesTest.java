package org.example.springboottodoapp.services;

import org.example.springboottodoapp.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.example.springboottodoapp.models.Task;

public class TaskServicesTest {
    @MockBean
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;


}
