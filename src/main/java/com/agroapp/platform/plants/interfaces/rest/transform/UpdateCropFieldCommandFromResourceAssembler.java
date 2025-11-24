package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.UpdateCropFieldCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.UpdateCropFieldResource;

public class UpdateCropFieldCommandFromResourceAssembler {
    public static UpdateCropFieldCommand toCommandFromResource(UpdateCropFieldResource resource) {
        return new UpdateCropFieldCommand(
                resource.cropFieldId(),
                resource.crop()
        );
    }
}

