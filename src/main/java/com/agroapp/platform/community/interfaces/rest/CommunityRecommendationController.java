package com.agroapp.platform.community.interfaces.rest;

import com.agroapp.platform.community.domain.model.queries.*;
import com.agroapp.platform.community.domain.services.*;
import com.agroapp.platform.community.interfaces.rest.resources.*;
import com.agroapp.platform.community.interfaces.rest.transform.*;
import com.agroapp.platform.community.domain.model.queries.GetAllCommunityRecommendationsQuery;
import com.agroapp.platform.community.domain.model.queries.GetCommunityRecommendationByIdQuery;
import com.agroapp.platform.community.domain.services.CommunityRecommendationCommandService;
import com.agroapp.platform.community.domain.services.CommunityRecommendationQueryService;
import com.agroapp.platform.community.interfaces.rest.resources.CommunityRecommendationResource;
import com.agroapp.platform.community.interfaces.rest.resources.UpdateCommunityRecommendationResource;
import com.agroapp.platform.community.interfaces.rest.transform.CommunityRecommendationResourceFromEntityAssembler;
import com.agroapp.platform.community.interfaces.rest.transform.UpdateCommunityRecommendationCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/CommunityRecommendation")
@Tag(name = "CommunityRecommendation", description = "Community Recommendation Endpoints")
public class CommunityRecommendationController {

    private final CommunityRecommendationCommandService communityRecommendationCommandService;
    private final CommunityRecommendationQueryService communityRecommendationQueryService;

    public CommunityRecommendationController(CommunityRecommendationCommandService communityRecommendationCommandService,
                                             CommunityRecommendationQueryService communityRecommendationQueryService) {
        this.communityRecommendationCommandService = communityRecommendationCommandService;
        this.communityRecommendationQueryService = communityRecommendationQueryService;
    }

    @GetMapping("/{recommendationId}")
    public ResponseEntity<CommunityRecommendationResource> getCommunityRecommendationById(@PathVariable Long recommendationId) {
        var query = new GetCommunityRecommendationByIdQuery(recommendationId);
        var recommendation = communityRecommendationQueryService.handle(query);
        if (recommendation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var recommendationResource = CommunityRecommendationResourceFromEntityAssembler.toResourceFromEntity(recommendation.get());
        return ResponseEntity.ok(recommendationResource);
    }

    @GetMapping
    public ResponseEntity<List<CommunityRecommendationResource>> getAllCommunityRecommendations() {
        var query = new GetAllCommunityRecommendationsQuery();
        var recommendations = communityRecommendationQueryService.handle(query);
        var recommendationResources = recommendations.stream()
                .map(CommunityRecommendationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(recommendationResources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommunityRecommendationResource> updateCommunityRecommendation(@PathVariable Long id, @RequestBody UpdateCommunityRecommendationResource resource) {
        var command = UpdateCommunityRecommendationCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var recommendation = communityRecommendationCommandService.handle(command);
        if (recommendation.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var recommendationResource = CommunityRecommendationResourceFromEntityAssembler.toResourceFromEntity(recommendation.get());
        return ResponseEntity.ok(recommendationResource);
    }
}

