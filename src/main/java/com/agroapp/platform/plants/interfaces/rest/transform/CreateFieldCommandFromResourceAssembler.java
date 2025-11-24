package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.CreateFieldCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateFieldResource;

public class CreateFieldCommandFromResourceAssembler {
    public static CreateFieldCommand toCommandFromResource(CreateFieldResource resource) {
        return new CreateFieldCommand(
                resource.userId(),
                resource.imageUrl(),
                resource.name(),
                resource.location(),
                resource.fieldSize()
        );
    }
}

