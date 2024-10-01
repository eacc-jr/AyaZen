package com.ayazen.AyaZen.service;

import com.ayazen.AyaZen.entity.AyazenProject;
import com.ayazen.AyaZen.entity.AyazenTask;
import com.ayazen.AyaZen.repository.AyazenTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class AyazenTaskServiceImpl implements AyazenTaskService {

    @Autowired
    private AyazenTaskRepository taskRepository;

    @Autowired
    private AyazenProjectService projectService;

    @Override
    public List<AyazenTask> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public AyazenTask getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
    }

    @Override
    public AyazenTask createTask(AyazenTask task) {
        return taskRepository.save(task);
    }

    @Override
    public AyazenTask updateTask(Long id, AyazenTask task) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
        }
        task.setId(id);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public AyazenTask assignTaskToProject(Long taskId, Long projectId) {
        AyazenTask task = getTaskById(taskId);
        AyazenProject project = projectService.getProjectById(projectId);
        task.setProject(project);
        return taskRepository.save(task);
    }
}
