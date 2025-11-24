package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.entities.CropField;
import com.agroapp.platform.plants.interfaces.rest.resources.CropFieldResource;

public class CropFieldResourceFromEntityAssembler {
    public static CropFieldResource toResourceFromEntity(CropField cropField) {
        return new CropFieldResource(
                cropField.getId(),
                cropField.getFieldId(),
                cropField.getCrop(),
                cropField.getSoilType(),
                cropField.getSunlight(),
                cropField.getWatering(),
                cropField.getPlantingDate(),
                cropField.getHarvestDate(),
                cropField.getStatus(),
                cropField.getCreatedAt() != null ? cropField.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                cropField.getUpdatedAt() != null ? cropField.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}

