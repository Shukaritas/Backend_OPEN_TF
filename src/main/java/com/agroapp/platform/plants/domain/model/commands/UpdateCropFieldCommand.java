package com.agroapp.platform.plants.domain.model.commands;

public record UpdateCropFieldCommand(
        Long cropFieldId,
        String crop
) {
}

