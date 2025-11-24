package com.agroapp.platform.plants.interfaces.rest;

import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.services.*;
import com.agroapp.platform.plants.interfaces.rest.resources.*;
import com.agroapp.platform.plants.interfaces.rest.transform.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllCropFieldsQuery;
import com.agroapp.platform.plants.domain.model.queries.GetCropFieldByFieldIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetCropFieldByIdQuery;
import com.agroapp.platform.plants.domain.services.CropFieldCommandService;
import com.agroapp.platform.plants.domain.services.CropFieldQueryService;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateCropFieldResource;
import com.agroapp.platform.plants.interfaces.rest.resources.CropFieldResource;
import com.agroapp.platform.plants.interfaces.rest.resources.UpdateCropFieldResource;
import com.agroapp.platform.plants.interfaces.rest.transform.CreateCropFieldCommandFromResourceAssembler;
import com.agroapp.platform.plants.interfaces.rest.transform.CropFieldResourceFromEntityAssembler;
import com.agroapp.platform.plants.interfaces.rest.transform.UpdateCropFieldCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/CropFields")
@Tag(name = "CropFields", description = "CropField Management Endpoints")
public class CropFieldsController {

    private final CropFieldCommandService cropFieldCommandService;
    private final CropFieldQueryService cropFieldQueryService;

    public CropFieldsController(CropFieldCommandService cropFieldCommandService,
                                CropFieldQueryService cropFieldQueryService) {
        this.cropFieldCommandService = cropFieldCommandService;
        this.cropFieldQueryService = cropFieldQueryService;
    }

    @PostMapping
    public ResponseEntity<CropFieldResource> createCropField(@RequestBody CreateCropFieldResource resource) {
        var command = CreateCropFieldCommandFromResourceAssembler.toCommandFromResource(resource);
        var cropField = cropFieldCommandService.handle(command);
        if (cropField.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cropFieldResource = CropFieldResourceFromEntityAssembler.toResourceFromEntity(cropField.get());
        return new ResponseEntity<>(cropFieldResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CropFieldResource>> getAllCropFields() {
        var query = new GetAllCropFieldsQuery();
        var cropFields = cropFieldQueryService.handle(query);
        var cropFieldResources = cropFields.stream()
                .map(CropFieldResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(cropFieldResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CropFieldResource> getCropFieldById(@PathVariable Long id) {
        var query = new GetCropFieldByIdQuery(id);
        var cropField = cropFieldQueryService.handle(query);
        if (cropField.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cropFieldResource = CropFieldResourceFromEntityAssembler.toResourceFromEntity(cropField.get());
        return ResponseEntity.ok(cropFieldResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CropFieldResource> updateCropField(@PathVariable Long id, @RequestBody UpdateCropFieldResource resource) {
        var command = UpdateCropFieldCommandFromResourceAssembler.toCommandFromResource(
                new UpdateCropFieldResource(id, resource.crop())
        );
        var cropField = cropFieldCommandService.handle(command);
        if (cropField.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cropFieldResource = CropFieldResourceFromEntityAssembler.toResourceFromEntity(cropField.get());
        return ResponseEntity.ok(cropFieldResource);
    }

    @GetMapping("/field/{fieldId}")
    public ResponseEntity<CropFieldResource> getCropFieldByFieldId(@PathVariable Long fieldId) {
        var query = new GetCropFieldByFieldIdQuery(fieldId);
        var cropField = cropFieldQueryService.handle(query);
        if (cropField.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cropFieldResource = CropFieldResourceFromEntityAssembler.toResourceFromEntity(cropField.get());
        return ResponseEntity.ok(cropFieldResource);
    }
}

