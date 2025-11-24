package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.aggregates.Task;
import com.agroapp.platform.plants.interfaces.rest.resources.TaskResource;

public class TaskResourceFromEntityAssembler {
    public static TaskResource toResourceFromEntity(Task task) {
        return new TaskResource(
                task.getId(),
                task.getFieldId(),
                task.getDescription(),
                task.getDueDate(),
                task.getCreatedAt() != null ? task.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                task.getUpdatedAt() != null ? task.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}

