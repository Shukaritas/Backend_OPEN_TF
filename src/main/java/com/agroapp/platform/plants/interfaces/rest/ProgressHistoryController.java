package com.agroapp.platform.plants.interfaces.rest;

import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.services.*;
import com.agroapp.platform.plants.interfaces.rest.resources.*;
import com.agroapp.platform.plants.interfaces.rest.transform.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllProgressHistoriesQuery;
import com.agroapp.platform.plants.domain.model.queries.GetProgressHistoryByIdQuery;
import com.agroapp.platform.plants.domain.services.ProgressHistoryCommandService;
import com.agroapp.platform.plants.domain.services.ProgressHistoryQueryService;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateProgressHistoryResource;
import com.agroapp.platform.plants.interfaces.rest.resources.ProgressHistoryResource;
import com.agroapp.platform.plants.interfaces.rest.resources.UpdateProgressHistoryResource;
import com.agroapp.platform.plants.interfaces.rest.transform.CreateProgressHistoryCommandFromResourceAssembler;
import com.agroapp.platform.plants.interfaces.rest.transform.ProgressHistoryResourceFromEntityAssembler;
import com.agroapp.platform.plants.interfaces.rest.transform.UpdateProgressHistoryCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/progress")
@Tag(name = "ProgressHistory", description = "ProgressHistory Management Endpoints")
public class ProgressHistoryController {

    private final ProgressHistoryCommandService progressHistoryCommandService;
    private final ProgressHistoryQueryService progressHistoryQueryService;

    public ProgressHistoryController(ProgressHistoryCommandService progressHistoryCommandService,
                                     ProgressHistoryQueryService progressHistoryQueryService) {
        this.progressHistoryCommandService = progressHistoryCommandService;
        this.progressHistoryQueryService = progressHistoryQueryService;
    }

    @GetMapping
    public ResponseEntity<List<ProgressHistoryResource>> getAllProgressHistories() {
        var query = new GetAllProgressHistoriesQuery();
        var progressHistories = progressHistoryQueryService.handle(query);
        var progressHistoryResources = progressHistories.stream()
                .map(ProgressHistoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(progressHistoryResources);
    }

    @PostMapping
    public ResponseEntity<ProgressHistoryResource> createProgressHistory(@RequestBody CreateProgressHistoryResource resource) {
        var command = CreateProgressHistoryCommandFromResourceAssembler.toCommandFromResource(resource);
        var progressHistory = progressHistoryCommandService.handle(command);
        if (progressHistory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var progressHistoryResource = ProgressHistoryResourceFromEntityAssembler.toResourceFromEntity(progressHistory.get());
        return new ResponseEntity<>(progressHistoryResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressHistoryResource> getProgressHistoryById(@PathVariable Long id) {
        var query = new GetProgressHistoryByIdQuery(id);
        var progressHistory = progressHistoryQueryService.handle(query);
        if (progressHistory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var progressHistoryResource = ProgressHistoryResourceFromEntityAssembler.toResourceFromEntity(progressHistory.get());
        return ResponseEntity.ok(progressHistoryResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressHistoryResource> updateProgressHistory(@PathVariable Long id, @RequestBody UpdateProgressHistoryResource resource) {
        var command = UpdateProgressHistoryCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var progressHistory = progressHistoryCommandService.handle(command);
        if (progressHistory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var progressHistoryResource = ProgressHistoryResourceFromEntityAssembler.toResourceFromEntity(progressHistory.get());
        return ResponseEntity.ok(progressHistoryResource);
    }
}

