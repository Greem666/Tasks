package com.crud.tasks.service;

import com.crud.tasks.domain.task.Task;
import com.crud.tasks.domain.task.TaskDto;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    public void getAllTasks() {
        // Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Test task", "Test content"));

        when(repository.findAll()).thenReturn(taskList);

        // When
        List<Task> tasks = dbService.getAllTasks();

        // Then
        assertEquals(1, tasks.size());
        assertEquals(1L, tasks.get(0).getId(), 0);
        assertEquals("Test task", tasks.get(0).getTitle());
        assertEquals("Test content", tasks.get(0).getContent());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getTaskById() {
        // Given
        Task task = new Task(1L, "Test task", "Test content");

        when(repository.findById(anyLong())).thenReturn(Optional.of(task));

        // When
        Optional<Task> retrievedTask = dbService.getTaskById(1L);

        // Then
        assertTrue(retrievedTask.isPresent());
        assertEquals(1L, retrievedTask.get().getId(), 0);
        assertEquals("Test task", retrievedTask.get().getTitle());
        assertEquals("Test content", retrievedTask.get().getContent());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void saveTask() {
        // Given
        Task task = new Task(1L, "Test task", "Test content");

        when(repository.save(any(Task.class))).thenReturn(task);

        // When
        Task savedTask = dbService.saveTask(task);

        // Then
        assertEquals(1L, savedTask.getId(), 0);
        assertEquals("Test task", savedTask.getTitle());
        assertEquals("Test content", savedTask.getContent());
        verify(repository, times(1)).save(any(Task.class));
    }

    @Test
    public void deleteTask() {
        // Given
        // -

        // When
        dbService.deleteTask(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}