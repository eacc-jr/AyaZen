package com.ayazen.AyaZen.controller;

import com.ayazen.AyaZen.service.AyazenTaskService;
import com.ayazen.AyaZen.entity.AyazenTask;
import com.ayazen.AyaZen.service.AyazenTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tasks")
public class AyazenTaskController {


    @Autowired
    private AyazenTaskService taskService;

    @GetMapping
    public ResponseEntity<List<AyazenTask>> getAllTasks() {
        List<AyazenTask> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AyazenTask> getTaskById(@PathVariable Long id) {
        AyazenTask task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<AyazenTask> createTask(@RequestBody AyazenTask task) {
        AyazenTask createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AyazenTask> updateTask(@PathVariable Long id, @RequestBody AyazenTask task) {
        AyazenTask updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}