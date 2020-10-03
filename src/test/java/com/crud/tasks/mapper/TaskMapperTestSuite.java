package com.crud.tasks.mapper;

import com.crud.tasks.domain.task.Task;
import com.crud.tasks.domain.task.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void shouldMapToTask() {
        // Given
        TaskDto taskDto = new TaskDto(1L, "test", "test_content");

        // When
        Task task = taskMapper.mapToTask(taskDto);

        // Then
        assertEquals(1L, task.getId(), 0);
        assertEquals("test", task.getTitle());
        assertEquals("test_content", task.getContent());
    }

    @Test
    public void shouldMapToTaskDto() {
        // Given
        Task task = new Task(1L, "test", "test_content");

        // When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        // Then
        assertEquals(1L, taskDto.getId(), 0);
        assertEquals("test", taskDto.getTitle());
        assertEquals("test_content", taskDto.getContent());
    }

    @Test
    public void shouldMapToTaskListDto() {
        // Given
        List<Task> tasks = Arrays.asList(
                new Task(1L, "test1", "test_content1"),
                new Task(2L, "test2", "test_content2")
        );

        // When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        // Then
        assertEquals(2, taskDtos.size());

        assertEquals(1L, taskDtos.get(0).getId(), 0);
        assertEquals("test1", taskDtos.get(0).getTitle());
        assertEquals("test_content1", taskDtos.get(0).getContent());

        assertEquals(2L, taskDtos.get(1).getId(), 0);
        assertEquals("test2", taskDtos.get(1).getTitle());
        assertEquals("test_content2", taskDtos.get(1).getContent());
    }
}
