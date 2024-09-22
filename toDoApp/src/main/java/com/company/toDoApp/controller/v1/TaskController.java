package com.company.toDoApp.controller.v1;

import com.company.toDoApp.model.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.model.dto.Request.Filter.TaskFilterRequest;
import com.company.toDoApp.model.dto.Request.Update.TaskUpdateRequest;
import com.company.toDoApp.model.dto.Response.TaskResponse;
import com.company.toDoApp.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/filter")
    public List<TaskResponse> filterTasks(TaskFilterRequest taskFilterRequest) {
        return taskService.filterTasks(taskFilterRequest);
    }
    @GetMapping("/all")
    public List<TaskResponse> getTasks() {
        return taskService.getAllTasks();
    }
    @GetMapping("/{id}")
    public TaskResponse findTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable int id) {
        taskService.deleteTaskById(id);
    }

    @PostMapping
    public void createTask(@RequestBody TaskCreateRequest request) {
        taskService.createTask(request);
    }

    @PutMapping("/{id}")
    public void updateTask(@RequestBody TaskUpdateRequest request) {
        taskService.updateTask(request);
    }
}
