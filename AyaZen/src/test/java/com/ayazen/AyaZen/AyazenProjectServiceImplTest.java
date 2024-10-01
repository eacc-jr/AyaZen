package com.ayazen.AyaZen;


import com.ayazen.AyaZen.entity.AyazenProject;
import com.ayazen.AyaZen.entity.AyazenTask;
import com.ayazen.AyaZen.repository.AyazenProjectRepository;
import com.ayazen.AyaZen.service.AyazenProjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AyazenProjectServiceImplTest {

    @Mock
    private AyazenProjectRepository projectRepository;

    @InjectMocks
    private AyazenProjectServiceImpl projectService;

    @Test
    public void testGetAllProjects() {
        List<AyazenProject> projects = new ArrayList<>();
        projects.add(new AyazenProject());
        projects.add(new AyazenProject());
        when(projectRepository.findAll()).thenReturn(projects);

        List<AyazenProject> result = projectService.getAllProjects();

        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void testGetProjectById_ExistingProject() {
        AyazenProject project = new AyazenProject();
        project.setId(1L);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        AyazenProject result = projectService.getProjectById(1L);

        assertEquals(1L, result.getId());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProjectById_NonExistingProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> projectService.getProjectById(1L));
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateProject() {
        AyazenProject project = new AyazenProject();
        project.setName("Novo Projeto");
        when(projectRepository.save(any(AyazenProject.class))).thenReturn(project);

        AyazenProject result = projectService.createProject(project);

        assertEquals("Novo Projeto", result.getName());
        verify(projectRepository, times(1)).save(project);
    }


    @Test
    public void testUpdateProject_NonExistingProject() {
        AyazenProject updatedProject = new AyazenProject();
        updatedProject.setName("Projeto Atualizado");
        when(projectRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> projectService.updateProject(1L, updatedProject));
        verify(projectRepository, times(1)).existsById(1L);
        verify(projectRepository, never()).save(any());
    }

    @Test
    public void testDeleteProject_ExistingProject() {
        when(projectRepository.existsById(1L)).thenReturn(true);
        doNothing().when(projectRepository).deleteById(1L);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).existsById(1L);
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteProject_NonExistingProject() {
        when(projectRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> projectService.deleteProject(1L));
        verify(projectRepository, times(1)).existsById(1L);
        verify(projectRepository, never()).deleteById(any());
    }

    @Test
    public void testGetTasksByProjectId_ExistingProject() {
        AyazenProject project = new AyazenProject();
        project.setId(1L);
        List<AyazenTask> tasks = new ArrayList<>();
        tasks.add(new AyazenTask());
        tasks.add(new AyazenTask());
        project.setTasks(tasks);

        when(projectRepository.existsById(1L)).thenReturn(true);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        List<AyazenTask> result = projectService.getTasksByProjectId(1L);

        assertEquals(2, result.size());
        verify(projectRepository, times(1)).existsById(1L);
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTasksByProjectId_NonExistingProject() {
        when(projectRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> projectService.getTasksByProjectId(1L));
        verify(projectRepository, times(1)).existsById(1L);
        verify(projectRepository, never()).findById(any());
    }

    @Test
    public void testAddTaskToProject_ExistingProject() {
        AyazenProject project = new AyazenProject();
        project.setId(1L);
        project.setTasks(new ArrayList<>());

        AyazenTask task = new AyazenTask();
        task.setName("Nova Tarefa");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(AyazenProject.class))).thenReturn(project);

        AyazenProject result = projectService.addTaskToProject(1L, task);

        assertEquals(1, result.getTasks().size());
        assertEquals("Nova Tarefa", result.getTasks().get(0).getName());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testAddTaskToProject_NonExistingProject() {
        AyazenTask task = new AyazenTask();
        task.setName("Nova Tarefa");

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> projectService.addTaskToProject(1L, task),
                "Deveria lançar exceção quando o projeto não for encontrado");

        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, never()).save(any());
    }


}
