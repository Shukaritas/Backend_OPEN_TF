package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.CreateTaskCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateTaskResource;

public class CreateTaskCommandFromResourceAssembler {
    public static CreateTaskCommand toCommandFromResource(CreateTaskResource resource) {
        return new CreateTaskCommand(
                resource.fieldId(),
                resource.description(),
                resource.dueDate()
        );
    }
}

