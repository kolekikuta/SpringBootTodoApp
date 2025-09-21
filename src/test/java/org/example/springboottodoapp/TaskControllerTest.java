package org.example.springboottodoapp;

import org.example.springboottodoapp.controllers.TaskController;
import org.example.springboottodoapp.models.Task;
import org.example.springboottodoapp.services.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

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
    public void testCreateTask() throws Exception {
        Task newTask = new Task("New Task", false);
        Mockito.when(taskService.createNewTask(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post("/api/v1/tasks/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"task\":  \"New Task\", \"completed\":  false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.task").value("New Task"))
                .andExpect(jsonPath("$.completed").value(false));
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
        Task task = new Task(1L, "Task 1", false);
        Mockito.when(taskService.findTaskById(1L)).thenReturn(task);
        Mockito.doNothing().when(taskService).deleteTask(task);

        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
