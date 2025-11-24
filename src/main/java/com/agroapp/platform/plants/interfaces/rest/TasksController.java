package com.agroapp.platform.plants.interfaces.rest;

import com.agroapp.platform.plants.domain.model.commands.DeleteTaskCommand;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.services.*;
import com.agroapp.platform.plants.interfaces.rest.resources.*;
import com.agroapp.platform.plants.interfaces.rest.transform.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllTasksQuery;
import com.agroapp.platform.plants.domain.model.queries.GetTaskByIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetTasksByFieldIdQuery;
import com.agroapp.platform.plants.domain.services.TaskCommandService;
import com.agroapp.platform.plants.domain.services.TaskQueryService;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateTaskResource;
import com.agroapp.platform.plants.interfaces.rest.resources.EditTaskResource;
import com.agroapp.platform.plants.interfaces.rest.resources.TaskResource;
import com.agroapp.platform.plants.interfaces.rest.transform.CreateTaskCommandFromResourceAssembler;
import com.agroapp.platform.plants.interfaces.rest.transform.TaskResourceFromEntityAssembler;
import com.agroapp.platform.plants.interfaces.rest.transform.UpdateTaskCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Tasks")
@Tag(name = "Tasks", description = "Task Management Endpoints")
public class TasksController {

    private final TaskCommandService taskCommandService;
    private final TaskQueryService taskQueryService;

    public TasksController(TaskCommandService taskCommandService,
                           TaskQueryService taskQueryService) {
        this.taskCommandService = taskCommandService;
        this.taskQueryService = taskQueryService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResource>> getAllTasks() {
        var query = new GetAllTasksQuery();
        var tasks = taskQueryService.handle(query);
        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(taskResources);
    }

    @PostMapping
    public ResponseEntity<TaskResource> createTask(@RequestBody CreateTaskResource resource) {
        var command = CreateTaskCommandFromResourceAssembler.toCommandFromResource(resource);
        var task = taskCommandService.handle(command);
        if (task.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());
        return new ResponseEntity<>(taskResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResource> getTaskById(@PathVariable Long id) {
        var query = new GetTaskByIdQuery(id);
        var task = taskQueryService.handle(query);
        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());
        return ResponseEntity.ok(taskResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResource> updateTask(@PathVariable Long id, @RequestBody EditTaskResource resource) {
        var command = UpdateTaskCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var task = taskCommandService.handle(command);
        if (task.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());
        return ResponseEntity.ok(taskResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        var command = new DeleteTaskCommand(id);
        taskCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/field/{fieldId}")
    public ResponseEntity<List<TaskResource>> getTasksByFieldId(@PathVariable Long fieldId) {
        var query = new GetTasksByFieldIdQuery(fieldId);
        var tasks = taskQueryService.handle(query);
        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(taskResources);
    }
}

