package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.UpdateTaskCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.EditTaskResource;

public class UpdateTaskCommandFromResourceAssembler {
    public static UpdateTaskCommand toCommandFromResource(Long taskId, EditTaskResource resource) {
        return new UpdateTaskCommand(
                taskId,
                resource.fieldId(),
                resource.description(),
                resource.dueDate()
        );
    }
}

