package com.ayazen.AyaZen.controller;

import com.ayazen.AyaZen.entity.AyazenProject;
import com.ayazen.AyaZen.entity.AyazenTask;
import com.ayazen.AyaZen.repository.AyazenProjectRepository;
import com.ayazen.AyaZen.service.AyazenProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class AyazenProjectController {

    @Autowired
    private AyazenProjectRepository projectRepository;

    @Autowired
    private AyazenProjectService projectService;

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<AyazenTask>> getTasksByProjectId(@PathVariable Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            return ResponseEntity.notFound().build();
        }

        List<AyazenTask> tasks = projectRepository.findById(projectId).get().getTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping
    public List<AyazenProject> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public AyazenProject getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public AyazenProject createProject(@RequestBody AyazenProject project) {
        return projectService.createProject(project);
    }

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<AyazenProject> addTaskToProject(@PathVariable Long projectId, @RequestBody AyazenTask task) {
        AyazenProject updatedProject = projectService.addTaskToProject(projectId, task);
        return ResponseEntity.ok(updatedProject);
    }

    @PutMapping("/{id}")
    public AyazenProject updateProject(@PathVariable Long id, @RequestBody AyazenProject project) {
        project.setId(id);
        return projectRepository.save(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
    }
}
