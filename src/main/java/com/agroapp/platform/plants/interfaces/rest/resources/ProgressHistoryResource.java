package com.agroapp.platform.plants.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ProgressHistoryResource(
        Long id,
        Long fieldId,
        LocalDateTime watered,
        LocalDateTime fertilized,
        LocalDateTime pests,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}

