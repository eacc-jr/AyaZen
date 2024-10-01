package com.ayazen.AyaZen.service;

import com.ayazen.AyaZen.entity.AyazenTask;
import java.util.List;

public interface AyazenTaskService {

    List<AyazenTask> getAllTasks();

    AyazenTask getTaskById(Long id);

    AyazenTask createTask(AyazenTask task);

    AyazenTask updateTask(Long id, AyazenTask task);

    void deleteTask(Long id);

    AyazenTask assignTaskToProject(Long taskId, Long projectId);
}
