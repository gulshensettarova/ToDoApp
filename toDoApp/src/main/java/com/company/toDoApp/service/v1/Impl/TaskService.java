package com.company.toDoApp.service.v1.Impl;

import com.company.toDoApp.model.dao.entity.Project;
import com.company.toDoApp.model.dao.entity.TaskStatus;
import com.company.toDoApp.model.dao.repository.ProjectRepository;
import com.company.toDoApp.model.dao.repository.TaskStatusRepository;
import com.company.toDoApp.model.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.model.dao.entity.Task;
import com.company.toDoApp.model.dao.repository.TaskRepository;
import com.company.toDoApp.mapper.TaskMapper;
import com.company.toDoApp.security.UserPrincipal;
import com.company.toDoApp.service.v1.Inter.TaskInterface;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements TaskInterface {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TaskStatusImpl taskStatusService;
    private final ProjectRepository projectRepository;

    public TaskService(TaskMapper taskMapper, TaskRepository taskRepository, ProjectRepository projectRepository, TaskStatusImpl taskStatusService) {
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
        int projectId=getCurrentProjectId((id));
        if(checkProjectTeamLead(projectId)){
            Task deletedTask=taskRepository.getById(id);
            deletedTask.setAcive(false);
            taskRepository.save(deletedTask);
            return true;
        }

        return false;
    }

    public int getCurrentProjectId(int taskId){
        int currentProjectId=taskRepository.findById(taskId).get().getProject().getId();
        return currentProjectId;
    }

    //gonderilen projectId-e gore onun teamLead-i tapilir ve sessiyada olan adamla id-i muqayise olunur
    public Boolean checkProjectTeamLead(int projectId){
        Project currentProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Layihə tapılmadı"));
        int currentTeamLead=currentProject.getTeam().getTeamLeaderId();
        if(currentTeamLead == getCurrentUserId()){
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

    @Override
    public Boolean updateTask() {
        return null;
    }

    @Override
    public Boolean changeStatus(int taskId,TaskStatusEnum newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task tapılmadı"));

        // Taskın aid olduğu layihənin ID-sini tapmaq
        int projectId = getCurrentProjectId(taskId);
        // Əgər hal-hazırda login olan istifadəçi layihənin komanda rəhbəridirsə
        if (checkProjectTeamLead(projectId)) {
            if(newStatus.equals(TaskStatusEnum.CANCELLED) ||
                    newStatus.equals(TaskStatusEnum.COMPLETED) ||
                    newStatus.equals(TaskStatusEnum.NOT_COMPLETED)){
                // TaskStatusEnum-u TaskStatus entitiyə çevirmək
                TaskStatus statusEntity = taskStatusService.findByEnum(newStatus);
                // Task-ın statusunu yeniləyirik
                task.setStatus(statusEntity);
                // Yenilənmiş task-ı saxlayırıq
                taskRepository.save(task);
                return true; // Status uğurla dəyişdirildi
            }
            else {
                throw new RuntimeException("Yalnız komanda rəhbəri taskın statusunu dəyişə bilər.");
            }

        }
        // Əgər hal-hazırda login olan istifadəçi taskı icra etmeli olan iscidirse
        //bura tamamlanmali
        //tekrar kodlar metoda alinmali
        else  if(1==1){
            if(newStatus.equals(TaskStatusEnum.IN_PROGRESS) ||
                    newStatus.equals(TaskStatusEnum.ON_HOLD)){
                // TaskStatusEnum-u TaskStatus entitiyə çevirmək
                TaskStatus statusEntity = taskStatusService.findByEnum(newStatus);
                // Task-ın statusunu yeniləyirik
                task.setStatus(statusEntity);
                // Yenilənmiş task-ı saxlayırıq
                taskRepository.save(task);
                return true; // Status uğurla dəyişdirildi
            }

        }

        return null;
    }

    @Override
    public TaskStatusEnum getStatus() {
        return null;
    }
}
