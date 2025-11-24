package com.agroapp.platform.community.infrastructure.persistence.jpa.repositories;

import com.agroapp.platform.community.domain.model.aggregates.CommunityRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRecommendationRepository extends JpaRepository<CommunityRecommendation, Long> {
}

