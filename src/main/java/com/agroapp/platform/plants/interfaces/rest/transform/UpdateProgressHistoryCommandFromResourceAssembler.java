package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.UpdateProgressHistoryCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.UpdateProgressHistoryResource;

public class UpdateProgressHistoryCommandFromResourceAssembler {
    public static UpdateProgressHistoryCommand toCommandFromResource(Long progressHistoryId, UpdateProgressHistoryResource resource) {
        return new UpdateProgressHistoryCommand(
                progressHistoryId,
                resource.watered(),
                resource.fertilized(),
                resource.pests()
        );
    }
}

