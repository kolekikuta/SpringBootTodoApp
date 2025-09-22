package org.example.springboottodoapp.mapper;

import org.example.springboottodoapp.dto.TaskResponseDTO;
import org.mapstruct.Mapper;
import org.example.springboottodoapp.models.Task;
import org.example.springboottodoapp.dto.CreateTaskDTO;


@Mapper(componentModel = "spring")
public interface TaskMapper {
    CreateTaskDTO toCreateDto(Task task);
    TaskResponseDTO toResponseDto(Task task);
}
