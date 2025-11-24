package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.CreateCropFieldCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateCropFieldResource;

public class CreateCropFieldCommandFromResourceAssembler {
    public static CreateCropFieldCommand toCommandFromResource(CreateCropFieldResource resource) {
        return new CreateCropFieldCommand(
                resource.fieldId(),
                resource.crop(),
                resource.soilType(),
                resource.sunlight(),
                resource.watering(),
                resource.plantingDate(),
                resource.harvestDate(),
                resource.status()
        );
    }
}

