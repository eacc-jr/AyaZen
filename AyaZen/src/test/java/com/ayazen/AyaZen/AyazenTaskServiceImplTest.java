package com.ayazen.AyaZen;
import com.ayazen.AyaZen.entity.AyazenProject;
import com.ayazen.AyaZen.entity.AyazenTask;
import com.ayazen.AyaZen.repository.AyazenTaskRepository;
import com.ayazen.AyaZen.service.AyazenTaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import com.ayazen.AyaZen.service.AyazenProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AyazenTaskServiceImplTest {

    @Mock
    private AyazenTaskRepository taskRepository;

    @Mock
    private AyazenProjectService projectService;

    @InjectMocks
    private AyazenTaskServiceImpl taskService;

    @Test
    public void testGetAllTasks() {
        List<AyazenTask> tasks = new ArrayList<>();
        tasks.add(new AyazenTask());
        tasks.add(new AyazenTask());
        when(taskRepository.findAll()).thenReturn(tasks);

        List<AyazenTask> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById_ExistingTask() {
        AyazenTask task = new AyazenTask();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        AyazenTask result = taskService.getTaskById(1L);

        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTaskById_NonExistingTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> taskService.getTaskById(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateTask() {
        AyazenTask task = new AyazenTask();
        task.setName("Nova Tarefa");
        when(taskRepository.save(any(AyazenTask.class))).thenReturn(task);

        AyazenTask result = taskService.createTask(task);

        assertEquals("Nova Tarefa", result.getName());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask_ExistingTask() {
        AyazenTask existingTask = new AyazenTask();
        existingTask.setId(1L);
        existingTask.setName("Tarefa Existente");
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.save(any(AyazenTask.class))).thenReturn(existingTask);

        AyazenTask updatedTask = new AyazenTask();
        updatedTask.setName("Tarefa Atualizada");

        AyazenTask result = taskService.updateTask(1L, updatedTask);

        assertEquals(1L, result.getId());
        assertEquals("Tarefa Atualizada", result.getName());
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).save(updatedTask);
    }

    @Test
    public void testUpdateTask_NonExistingTask() {
        AyazenTask updatedTask = new AyazenTask();
        updatedTask.setName("Tarefa Atualizada");
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> taskService.updateTask(1L, updatedTask));
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testDeleteTask_ExistingTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTask_NonExistingTask() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, never()).deleteById(any());
    }

    @Test
    public void testAssignTaskToProject_ExistingTaskAndProject() {
        AyazenTask task = new AyazenTask();
        task.setId(1L);
        AyazenProject project = new AyazenProject();
        project.setId(2L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(projectService.getProjectById(2L)).thenReturn(project);
        when(taskRepository.save(any(AyazenTask.class))).thenReturn(task);

        AyazenTask result = taskService.assignTaskToProject(1L, 2L);

        assertEquals(2L, result.getProject().getId());
        verify(taskRepository, times(1)).findById(1L);
        verify(projectService, times(1)).getProjectById(2L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testAssignTaskToProject_NonExistingTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        AyazenProject project = new AyazenProject();
        project.setId(2L);
        when(projectService.getProjectById(2L)).thenReturn(project);

        assertThrows(ResponseStatusException.class, () -> taskService.assignTaskToProject(1L, 2L));

        verify(taskRepository, times(1)).findById(1L);
        verify(projectService, never()).getProjectById(any());
        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testAssignTaskToProject_NonExistingProject() {
        AyazenTask task = new AyazenTask();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(projectService.getProjectById(2L)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> taskService.assignTaskToProject(1L, 2L));

        verify(taskRepository, times(1)).findById(1L);
        verify(projectService, times(1)).getProjectById(2L);
        verify(taskRepository, never()).save(any());
    }

}
