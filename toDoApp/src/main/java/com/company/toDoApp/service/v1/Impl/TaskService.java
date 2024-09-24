package com.company.toDoApp.service.v1.Impl;

import com.company.toDoApp.command.taskstatus.EmployeeStatusChangeCommand;
import com.company.toDoApp.command.taskstatus.TaskStatusChangeCommand;
import com.company.toDoApp.command.taskstatus.TeamLeadStatusChangeCommand;
import com.company.toDoApp.model.dao.entity.Project;
import com.company.toDoApp.model.dao.entity.TaskStatus;
import com.company.toDoApp.model.dao.repository.ProjectRepository;
import com.company.toDoApp.model.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.model.dao.entity.Task;
import com.company.toDoApp.model.dao.repository.TaskRepository;
import com.company.toDoApp.mapper.TaskMapper;
import com.company.toDoApp.security.UserPrincipal;
import com.company.toDoApp.service.v1.Inter.TaskInterface;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements TaskInterface {

    //Burada design pattern tedbiq olunub optimallasdirilacaq (Command Pattern)
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TaskStatusService taskStatusService;
    private final ProjectRepository projectRepository;


    public TaskService(TaskMapper taskMapper, TaskRepository taskRepository, ProjectRepository projectRepository, TaskStatusService taskStatusService) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskStatusService = taskStatusService;

    }

    @Override
    public Task newTask(TaskCreateRequest request) {
        if(checkProjectTeamLead(request.getProject().getId())){
            Task newTask=taskMapper.toEntity(request);
            taskRepository.save(newTask);
            return newTask;
        }
        return null;
    }

    @Override
    public Boolean deleteTask(int id) {
        Task deletedTask = findTaskById(id);
        int projectId = deletedTask.getProject().getId();
        if(checkProjectTeamLead(projectId)){
            deletedTask.setAcive(false);
            taskRepository.save(deletedTask);
            return true;
        }

        return false;
    }

    //gonderilen projectId-e gore onun teamLead-i tapilir ve sessiyada olan adamla id-i muqayise olunur
    public Boolean checkProjectTeamLead(int projectId) {
        Project currentProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Layihə tapılmadı"));
        return currentProject.getTeam().getTeamLeaderId() == getCurrentUserId();
    }

    //Sessiyada olan cari user-in id-ini tapmaq ucun bu metoddan istifade edirik
    public int getCurrentUserId() {
        UserPrincipal currentUser=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currentUser.getId();
    }
    public int getTaskEmployeeId(int taskId){
        return taskRepository.findById(taskId).get().getEmployee().getId();
    }

    @Override
    public Boolean updateTask() {
        return null;
    }

    private Task findTaskById(int taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task tapılmadı"));
    }
    @Override
    public Boolean changeStatus(int taskId, TaskStatusEnum newStatus) {
        Task task = findTaskById(taskId);
        int projectId = task.getProject().getId();
        TaskStatusChangeCommand command;
        // TeamLead
        if (checkProjectTeamLead(projectId)) {
            command = new TeamLeadStatusChangeCommand(newStatus, taskStatusService);
        }
        // Employee
        else if (getCurrentUserId() == getTaskEmployeeId(taskId)) {
            command = new EmployeeStatusChangeCommand(newStatus, taskStatusService);
        } else {
            throw new RuntimeException("Səlahiyyətiniz yoxdur.");
        }
        // Status dəyişmə əmrini icra edirik
        command.execute(task);
        taskRepository.save(task);
        return true;
    }

    @Override
    public TaskStatusEnum getStatus() {
        return null;
    }
}
