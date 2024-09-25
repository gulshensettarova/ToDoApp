package com.company.toDoApp.service.v1.impl.task;

import com.company.toDoApp.command.taskstatus.EmployeeStatusChangeCommand;
import com.company.toDoApp.command.taskstatus.TaskStatusChangeCommand;
import com.company.toDoApp.command.taskstatus.TeamLeadStatusChangeCommand;
import com.company.toDoApp.exceptions.BaseException;
import com.company.toDoApp.model.dao.entity.Project;
import com.company.toDoApp.model.dao.repository.ProjectRepository;
import com.company.toDoApp.model.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.model.dto.Request.Filter.TaskFilterRequest;
import com.company.toDoApp.model.dto.Response.Base.SuccessResponse;
import com.company.toDoApp.model.dto.Response.TaskResponse;
import com.company.toDoApp.model.enums.error.ResponseCodes;
import com.company.toDoApp.model.enums.task.TaskStatusEnum;
import com.company.toDoApp.model.dao.entity.Task;
import com.company.toDoApp.model.dao.repository.TaskRepository;
import com.company.toDoApp.mapper.TaskMapper;
import com.company.toDoApp.security.UserPrincipal;
import com.company.toDoApp.service.v1.inter.task.TaskInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TaskService implements TaskInterface {

    @PersistenceContext
    private EntityManager entityManager;

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TaskStatusService taskStatusService;
    private final ProjectRepository projectRepository;



    @Override
    public SuccessResponse<Object> newTask(TaskCreateRequest request) {
        if(checkProjectTeamLead(request.getProject().getId())){
            Task newTask=taskMapper.toEntity(request);
            taskRepository.save(newTask);
            return SuccessResponse.createBaseResponse(newTask,ResponseCodes.SUCCESS);
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
                .orElseThrow(() -> BaseException.of(ResponseCodes.TASK_NOT_FOUND));
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
                .orElseThrow(() -> BaseException.of(ResponseCodes.TASK_NOT_FOUND));
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
            throw  BaseException.of(ResponseCodes.ACCESS_DENIED);
        }
        // Status dəyişmə əmrini icra edirik
        command.execute(task);
        taskRepository.save(task);
        return true;
    }

    @Override
    public TaskResponse getTaskById(int id) {
        return  taskRepository
                .findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(()->BaseException.of(ResponseCodes.TASK_NOT_FOUND));
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        return taskList
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public List<TaskResponse> filterTasks(TaskFilterRequest request) {
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq=cb.createQuery(Task.class);
        Root<Task> queryRoot=cq.from(Task.class);

        List<Predicate> predicates=new ArrayList<>();
        if(request.getCategoryId()!=null){
            predicates.add(cb.equal(queryRoot.get("categoryId"), request.getCategoryId()));
        }
        if(request.getProjectId()!=null){
            predicates.add(cb.equal(queryRoot.get("projectId"), request.getProjectId()));
        }
        if(request.getDeadline()!=null){
            predicates.add(cb.equal(queryRoot.get("deadline"), request.getDeadline()));
        }
        if(request.getPriority()!=null){
            predicates.add(cb.equal(queryRoot.get("priority"), request.getPriority()));
        }
        if(request.getTitle()!=null){
            predicates.add(cb.equal(queryRoot.get("title"), request.getTitle()));
        }
        if(request.getStatusId()!=null){
            predicates.add(cb.equal(queryRoot.get("statusId"), request.getStatusId()));
        }
        if(request.getEmployeeId()!=null){
            predicates.add(cb.equal(queryRoot.get("employeeId"), request.getEmployeeId()));
        }
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(cq).getResultList()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

}
