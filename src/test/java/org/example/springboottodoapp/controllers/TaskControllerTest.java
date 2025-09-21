package org.example.springboottodoapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springboottodoapp.TestConfig;
import org.example.springboottodoapp.models.Task;
import org.example.springboottodoapp.services.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = TaskController.class)
@Import(TestConfig.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(
                new Task("Task 1", false),
                new Task("Task 2", true)
        );
        Mockito.when(taskService.getAllTask()).thenReturn(tasks);

        mockMvc.perform(get("/api/v1/tasks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].task").value("Task 1"))
                .andExpect(jsonPath("$[1].completed").value(true));
    }

    @Test
    public void testGetAllCompletedTasks() throws Exception {
        Mockito.when(taskService.findAllCompletedTask())
                .thenReturn(List.of(new Task("Task2", true)));

        mockMvc.perform(get("/api/v1/tasks/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(true));
    }

    @Test
    public void testGetAllIncompleteTasks() throws Exception {
        Mockito.when(taskService.findAllInCompleteTask())
                .thenReturn(List.of(new Task("Task 3", false)));

        mockMvc.perform(get("/api/v1/tasks/incomplete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    public void testGetAllOverdueTasks() throws Exception {
       LocalDateTime now = LocalDateTime.now();
       Task task1 = new Task("Task 1", false, now.minusDays(1));
       Task task2 = new Task("Task 2", false, now.minusDays(2));
       Task task3 = new Task("Task 3", false, now.plusDays(3));

       List<Task> overdueTasks = Arrays.asList(task1, task2);

       Mockito.when(taskService.findAllOverdueTask())
               .thenReturn(overdueTasks);

       mockMvc.perform(get("/api/v1/tasks/overdue"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].task").value("Task 1"))
               .andExpect(jsonPath("$[1].task").value("Task 2"));
    }

    @Test
    public void testCreateTask() throws Exception {
        LocalDateTime now = LocalDateTime.of(2025, 9, 20, 12, 0, 0);

        Task newTask = new Task("New Task", false);
        newTask.setCreatedAt(now);

        Mockito.when(taskService.createNewTask(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post("/api/v1/tasks/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"task\":  \"New Task\", \"completed\":  false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.task").value("New Task"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.createdAt").value("2025-09-20T12:00:00"));
    }

    @Test
    public void testCreateTaskBadField() throws Exception {
        Task newTask = new Task();
        Mockito.when(taskService.createNewTask(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post("/api/v1/tasks/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"completed\":  false}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Task description is required."));

    }

    @Test
    public void testCreateTaskBadDueDate() throws Exception {
        Task invalidTask = new Task("Bad Task", false, LocalDateTime.now().minusDays(1));

        mockMvc.perform(post("/api/v1/tasks/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Due date must be after created date."));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task updatedTask = new Task("Updated Task", true);
        Mockito.when(taskService.updateTask(any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/api/v1/tasks/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"task\":  \"Updated Task\", \"completed\":  true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task").value("Updated Task"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void testDeleteTask() throws Exception {
        Task task = new Task("Task 1", false);
        Mockito.when(taskService.findTaskById(1L)).thenReturn(task);
        Mockito.doNothing().when(taskService).deleteTask(task);

        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
