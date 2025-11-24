package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.entities.ProgressHistory;
import com.agroapp.platform.plants.interfaces.rest.resources.ProgressHistoryResource;

public class ProgressHistoryResourceFromEntityAssembler {
    public static ProgressHistoryResource toResourceFromEntity(ProgressHistory progressHistory) {
        return new ProgressHistoryResource(
                progressHistory.getId(),
                progressHistory.getFieldId(),
                progressHistory.getWatered(),
                progressHistory.getFertilized(),
                progressHistory.getPests(),
                progressHistory.getCreatedAt() != null ? progressHistory.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                progressHistory.getUpdatedAt() != null ? progressHistory.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}

