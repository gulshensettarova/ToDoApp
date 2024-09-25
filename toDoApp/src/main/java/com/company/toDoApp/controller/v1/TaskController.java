package com.company.toDoApp.controller.v1;

import com.company.toDoApp.model.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.model.dto.Request.Filter.TaskFilterRequest;
import com.company.toDoApp.model.dto.Response.TaskResponse;
import com.company.toDoApp.service.v1.impl.task.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }

        @PostMapping
        public void createTask(@RequestBody TaskCreateRequest request) {
            taskService.newTask(request);
        }

        @DeleteMapping("/{id}")
        public void deleteTaskById(@PathVariable int id) {
            taskService.deleteTask(id);
        }


        @GetMapping("/all")
        public List<TaskResponse> getTasks() {
            return taskService.getAllTasks();
        }
        @GetMapping("/{id}")
        public TaskResponse findTaskById(@PathVariable int id) {
            return taskService.getTaskById(id);
        }

        @GetMapping("/filter")
        public List<TaskResponse> filterTasks(TaskFilterRequest taskFilterRequest) {
            return taskService.filterTasks(taskFilterRequest);
        }
//
//    @PutMapping("/{id}")
//    public void updateTask(@RequestBody TaskUpdateRequest request) {
//        taskService.updateTask(request);
//    }
}
