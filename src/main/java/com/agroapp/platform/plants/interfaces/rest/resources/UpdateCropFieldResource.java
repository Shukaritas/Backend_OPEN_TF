package com.agroapp.platform.plants.interfaces.rest.resources;

public record UpdateCropFieldResource(
        Long cropFieldId,
        String crop
) {
}

