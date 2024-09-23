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

    //Burada design pattern tedbiq olunub optimallasdirilacaq (Command Pattern)
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
            Task deletedTask = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Task tapılmadı"));
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
    public Boolean checkProjectTeamLead(int projectId) {
        Project currentProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Layihə tapılmadı"));
        return currentProject.getTeam().getTeamLeaderId() == getCurrentUserId();
    }

    //Sessiyada olan cari user-in id-ini tapmaq ucun bu metoddan istifade edirik
    public int getCurrentUserId() {
        UserPrincipal currentUser=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int currentUserId = currentUser.getId();
        return currentUserId;
    }
    public int getTaskEmployeeId(int taskId){
        return taskRepository.findById(taskId).get().getEmployee().getId();
    }

    @Override
    public Boolean updateTask() {
        return null;
    }
    public Boolean saveStatus(Task task,TaskStatusEnum status){
        TaskStatus statusEntity = taskStatusService.findByEnum(status);
        task.setStatus(statusEntity);
        taskRepository.save(task);
        return true;
    }
    private Task findTaskById(int taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task tapılmadı"));
    }
    @Override
    public Boolean changeStatus(int taskId, TaskStatusEnum newStatus) {
        Task task = findTaskById(taskId); // Taskı tapmaq üçün utility metoddan istifadə edirik
        int projectId = task.getProject().getId(); // Taskın aid olduğu layihənin ID-sini tapırıq

        // Statusa görə switch istifadə edirik
        switch (newStatus) {
            // Layihə rəhbərinin dəyişə biləcəyi statuslar
            case CANCELLED:
            case COMPLETED:
            case NOT_COMPLETED:
                if (checkProjectTeamLead(projectId)) {
                    saveStatus(task, newStatus); // Statusu yeniləyirik
                } else {
                    throw new RuntimeException("Yalnız komanda rəhbəri taskın statusunu dəyişə bilər.");
                }
                break;

            // Taskın icra olunmasını tələb edən işçinin dəyişə biləcəyi statuslar
            case IN_PROGRESS:
            case ON_HOLD:
                if (getCurrentUserId() == getTaskEmployeeId(taskId)) {
                    saveStatus(task, newStatus); // Statusu yeniləyirik
                } else {
                    throw new RuntimeException("Taskın icraçısı yalnız müəyyən statusları dəyişə bilər.");
                }
                break;

            // Əgər tanınmayan status verilərsə
            default:
                throw new RuntimeException("Mövcud olmayan status!");
        }

        return true;
    }

    @Override
    public TaskStatusEnum getStatus() {
        return null;
    }
}
