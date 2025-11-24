package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.aggregates.Field;
import com.agroapp.platform.plants.domain.model.queries.GetCropFieldByFieldIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetProgressHistoryByFieldIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetTasksByFieldIdQuery;
import com.agroapp.platform.plants.domain.services.CropFieldQueryService;
import com.agroapp.platform.plants.domain.services.ProgressHistoryQueryService;
import com.agroapp.platform.plants.domain.services.TaskQueryService;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.interfaces.rest.resources.FieldResource;

public class FieldResourceFromEntityAssembler {

    public static FieldResource toResourceFromEntity(Field field,
                                                      ProgressHistoryQueryService progressHistoryQueryService,
                                                      CropFieldQueryService cropFieldQueryService,
                                                      TaskQueryService taskQueryService) {

        var progressHistory = progressHistoryQueryService.handle(new GetProgressHistoryByFieldIdQuery(field.getId()));
        var cropField = cropFieldQueryService.handle(new GetCropFieldByFieldIdQuery(field.getId()));
        var tasks = taskQueryService.handle(new GetTasksByFieldIdQuery(field.getId()));

        return new FieldResource(
                field.getId(),
                field.getUserId(),
                field.getImageUrl(),
                field.getName(),
                field.getLocation(),
                field.getFieldSize(),
                progressHistory.map(ph -> ph.getId()).orElse(null),
                cropField.map(cf -> cf.getId()).orElse(null),
                tasks.stream().map(task -> task.getId()).toList(),
                field.getCreatedAt() != null ? field.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                field.getUpdatedAt() != null ? field.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}

