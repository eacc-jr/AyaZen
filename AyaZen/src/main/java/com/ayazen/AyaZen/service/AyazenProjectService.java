package com.ayazen.AyaZen.service;

import com.ayazen.AyaZen.entity.AyazenProject;
import com.ayazen.AyaZen.entity.AyazenTask;
import java.util.List;

public interface AyazenProjectService {

    List<AyazenProject> getAllProjects();

    AyazenProject getProjectById(Long id);

    AyazenProject createProject(AyazenProject project);

    AyazenProject updateProject(Long id, AyazenProject project);

    void deleteProject(Long id);

    List<AyazenTask> getTasksByProjectId(Long projectId);

    AyazenProject addTaskToProject(Long projectId, AyazenTask task);
}
