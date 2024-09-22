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
        //sessiyada olan cari userin id-i gotururuk
        UserPrincipal currentUser=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int currentUserId = currentUser.getId();

        //task hansi project-e aiddir onu tapiriq
        Project currentProject = projectRepository.findById(request.getProject().getId())
                .orElseThrow(() -> new RuntimeException("Layihə tapılmadı"));

        // Project-e aid olan komandanın rəhbərinin ID-sini əldə edirik
        int currentTeamLead=currentProject.getTeam().getTeamLeaderId();

        //yeni task yaratmaq selahiyyeti yalniz teamLead-de oldugu ucun sessiyada olan user-in id-i ile projectin teamLead-nin id-i muqayise olunur
        if(currentUserId==currentTeamLead){
            Task newTask=taskMapper.toEntity(request);
            taskRepository.save(newTask);

        }

        return null;
    }

    @Override
    public Boolean deleteTask() {
        return null;
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
