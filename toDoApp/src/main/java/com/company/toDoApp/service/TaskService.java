package com.company.toDoApp.service;

import com.company.toDoApp.model.dao.entity.Task;
import com.company.toDoApp.model.dao.repository.TaskRepository;
import com.company.toDoApp.model.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.model.dto.Request.Filter.TaskFilterRequest;
import com.company.toDoApp.model.dto.Request.Update.TaskUpdateRequest;
import com.company.toDoApp.model.dto.Response.TaskResponse;
import com.company.toDoApp.mapper.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    //SEHV
//    private TaskRepository taskRepository;
//    private TaskMapper  taskMapper;
//    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
//        this.taskRepository = taskRepository;
//        this.taskMapper = taskMapper;
//    }
//  public void createTask(TaskCreateRequest newtask) {
//        Task task=taskMapper.toEntity(newtask);
//        taskRepository.save(task);
//  }
//
//  public void deleteTaskById(int id) {
//        Task task=taskRepository.findById(id).orElseThrow();
//        task.setAcive(false);
//        taskRepository.save(task);
//  }
//  public List<TaskResponse> getAllTasks() {
//        List<Task> tasks=taskRepository.findAll();
//        return taskMapper.toDto(tasks);
//  }
//
//  public TaskResponse getTaskById(int id) {
//        Task task= taskRepository.findById(id).orElseThrow();
//        return taskMapper.toDto(task);
//  }
//
//  public List<TaskResponse> filterTasks(TaskFilterRequest taskFilterRequest) {
//         List<Task> tasks=taskRepository.findAll();
//  return tasks.stream()
//                 .filter(task->taskFilterRequest.getTitle()==null ||task.getTitle().contains(taskFilterRequest.getTitle()))
//                 .filter(task->taskFilterRequest.getPriority()==0 ||task.getPriority()==taskFilterRequest.getPriority())
//                 .map(taskMapper::toDto)
//                 .collect(Collectors.toList());
//
//
//  }
//  public void updateTask(TaskUpdateRequest request) {
//    taskRepository.save(buildCustomerWithUpdateRequest(request));
//    }
//
//  private Task buildCustomerWithUpdateRequest(TaskUpdateRequest taskUpdateRequest) {
//      return Task.builder()
//              .title(taskUpdateRequest.getTitle())
//              .priority(taskUpdateRequest.getPriority())
//              .deadline(taskUpdateRequest.getDeadline())
//              .description(taskUpdateRequest.getDescription())
//              .build();
//  }
}
