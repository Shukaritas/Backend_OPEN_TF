package com.agroapp.platform.community.application.internal.commandservices;

import com.agroapp.platform.community.domain.model.aggregates.CommunityRecommendation;
import com.agroapp.platform.community.domain.model.commands.UpdateCommunityRecommendationCommand;
import com.agroapp.platform.community.domain.services.CommunityRecommendationCommandService;
import com.agroapp.platform.community.infrastructure.persistence.jpa.repositories.CommunityRecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommunityRecommendationCommandServiceImpl implements CommunityRecommendationCommandService {

    private final CommunityRecommendationRepository communityRecommendationRepository;

    public CommunityRecommendationCommandServiceImpl(CommunityRecommendationRepository communityRecommendationRepository) {
        this.communityRecommendationRepository = communityRecommendationRepository;
    }

    @Override
    public Optional<CommunityRecommendation> handle(UpdateCommunityRecommendationCommand command) {
        Optional<CommunityRecommendation> recommendationOptional = communityRecommendationRepository.findById(command.recommendationId());

        if (recommendationOptional.isEmpty()) {
            throw new RuntimeException("CommunityRecommendation not found");
        }

        CommunityRecommendation recommendation = recommendationOptional.get();
        recommendation.update(command.userName(), command.comment());
        CommunityRecommendation updatedRecommendation = communityRecommendationRepository.save(recommendation);
        return Optional.of(updatedRecommendation);
    }
}

