package com.company.toDoApp.service.v1.Impl;

import com.company.toDoApp.model.dao.entity.Project;
import com.company.toDoApp.model.dao.repository.ProjectRepository;
import com.company.toDoApp.model.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.model.dao.entity.Task;
import com.company.toDoApp.model.dao.repository.TaskRepository;
import com.company.toDoApp.mapper.TaskMapper;
import com.company.toDoApp.security.UserPrincipal;
import com.company.toDoApp.service.v1.Inter.TaskInterface;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TaskService implements TaskInterface {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskMapper taskMapper, TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Task newTask(TaskCreateRequest request) {
        //yeni task yaratmaq selahiyyeti yalniz teamLead-de oldugu ucun sessiyada olan user-in id-i ile projectin teamLead-nin id-i muqayise olunur
        if(getCurrentUserId() ==getCurrentTeamLead(request.getProject().getId())){
            Task newTask=taskMapper.toEntity(request);
            taskRepository.save(newTask);
            return newTask;
        }
        return null;
    }

    @Override
    public Boolean deleteTask(int id) {
        int currentProjectId=taskRepository.findById(id).get().getProject().getId();
        if(getCurrentUserId() == getCurrentTeamLead(currentProjectId)){
            Task deletedTask=taskRepository.getById(id);
            deletedTask.setAcive(false);
            taskRepository.save(deletedTask);
            return true;
        }

        return false;
    }

    //Sessiyada olan cari user-in id-ini tapmaq ucun bu metoddan istifade edirik
    public int getCurrentUserId() {
        UserPrincipal currentUser=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int currentUserId = currentUser.getId();
        return currentUserId;
    }

    public int getCurrentTeamLead(int projectId) {
        //task hansi project-e aiddir onu tapiriq
        Project currentProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Layihə tapılmadı"));
        // Project-e aid olan komandanın rəhbərinin ID-sini əldə edirik
        int currentTeamLead=currentProject.getTeam().getTeamLeaderId();
        return currentTeamLead;
    }
    @Override
    public Boolean updateTask() {
        return null;
    }

    @Override
    public Boolean changeStatus() {
        return null;
    }

    @Override
    public TaskStatusEnum getStatus() {
        return null;
    }
}
