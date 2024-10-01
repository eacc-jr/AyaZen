package com.ayazen.AyaZen.service;


import org.springframework.stereotype.Service;

import com.ayazen.AyaZen.entity.AyazenProject;
import com.ayazen.AyaZen.entity.AyazenTask;
import com.ayazen.AyaZen.repository.AyazenProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AyazenProjectServiceImpl  implements AyazenProjectService{
    @Autowired
    private AyazenProjectRepository projectRepository;

    @Override
    public List<AyazenProject> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public AyazenProject getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));
    }

    @Override
    public AyazenProject createProject(AyazenProject project) {
        return projectRepository.save(project);
    }

    @Override
    public AyazenProject updateProject(Long id, AyazenProject project) {
        if (!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado");
        }
        project.setId(id);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado");
        }
        projectRepository.deleteById(id);
    }

    @Override
    public List<AyazenTask> getTasksByProjectId(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado");
        }
        return projectRepository.findById(projectId).get().getTasks();
    }

    @Override
    public AyazenProject addTaskToProject(Long projectId, AyazenTask task) {
        AyazenProject project = getProjectById(projectId);
        task.setProject(project);
        project.getTasks().add(task);
        return projectRepository.save(project);
    }
}
