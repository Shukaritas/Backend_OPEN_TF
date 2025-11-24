package com.agroapp.platform.plants.interfaces.rest;

import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.services.*;
import com.agroapp.platform.plants.interfaces.rest.resources.*;
import com.agroapp.platform.plants.interfaces.rest.transform.*;
import com.agroapp.platform.plants.domain.model.queries.GetFieldByIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetFieldsByUserIdQuery;
import com.agroapp.platform.plants.domain.services.*;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateFieldResource;
import com.agroapp.platform.plants.interfaces.rest.resources.FieldResource;
import com.agroapp.platform.plants.interfaces.rest.transform.CreateFieldCommandFromResourceAssembler;
import com.agroapp.platform.plants.interfaces.rest.transform.FieldResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Fields")
@Tag(name = "Fields", description = "Field Management Endpoints")
public class FieldsController {

    private final FieldCommandService fieldCommandService;
    private final FieldQueryService fieldQueryService;
    private final ProgressHistoryQueryService progressHistoryQueryService;
    private final CropFieldQueryService cropFieldQueryService;
    private final TaskQueryService taskQueryService;

    public FieldsController(FieldCommandService fieldCommandService,
                            FieldQueryService fieldQueryService,
                            ProgressHistoryQueryService progressHistoryQueryService,
                            CropFieldQueryService cropFieldQueryService,
                            TaskQueryService taskQueryService) {
        this.fieldCommandService = fieldCommandService;
        this.fieldQueryService = fieldQueryService;
        this.progressHistoryQueryService = progressHistoryQueryService;
        this.cropFieldQueryService = cropFieldQueryService;
        this.taskQueryService = taskQueryService;
    }

    @PostMapping
    public ResponseEntity<FieldResource> createField(@RequestBody CreateFieldResource resource) {
        var command = CreateFieldCommandFromResourceAssembler.toCommandFromResource(resource);
        var field = fieldCommandService.handle(command);
        if (field.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var fieldResource = FieldResourceFromEntityAssembler.toResourceFromEntity(
                field.get(),
                progressHistoryQueryService,
                cropFieldQueryService,
                taskQueryService
        );
        return new ResponseEntity<>(fieldResource, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FieldResource>> getFieldsByUserId(@PathVariable Long userId) {
        var query = new GetFieldsByUserIdQuery(userId);
        var fields = fieldQueryService.handle(query);
        var fieldResources = fields.stream()
                .map(field -> FieldResourceFromEntityAssembler.toResourceFromEntity(
                        field,
                        progressHistoryQueryService,
                        cropFieldQueryService,
                        taskQueryService
                ))
                .toList();
        return ResponseEntity.ok(fieldResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldResource> getFieldById(@PathVariable Long id) {
        var query = new GetFieldByIdQuery(id);
        var field = fieldQueryService.handle(query);
        if (field.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var fieldResource = FieldResourceFromEntityAssembler.toResourceFromEntity(
                field.get(),
                progressHistoryQueryService,
                cropFieldQueryService,
                taskQueryService
        );
        return ResponseEntity.ok(fieldResource);
    }
}

