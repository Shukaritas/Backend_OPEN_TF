package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.CreateProgressHistoryCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateProgressHistoryResource;

public class CreateProgressHistoryCommandFromResourceAssembler {
    public static CreateProgressHistoryCommand toCommandFromResource(CreateProgressHistoryResource resource) {
        return new CreateProgressHistoryCommand(
                resource.fieldId(),
                resource.watered(),
                resource.fertilized(),
                resource.pests()
        );
    }
}

