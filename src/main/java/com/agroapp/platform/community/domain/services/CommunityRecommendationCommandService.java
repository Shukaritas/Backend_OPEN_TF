package com.agroapp.platform.community.domain.services;

import com.agroapp.platform.community.domain.model.aggregates.CommunityRecommendation;
import com.agroapp.platform.community.domain.model.commands.*;
import com.agroapp.platform.community.domain.model.commands.UpdateCommunityRecommendationCommand;

import java.util.Optional;

public interface CommunityRecommendationCommandService {
    Optional<CommunityRecommendation> handle(UpdateCommunityRecommendationCommand command);
}

