package com.agroapp.platform.plants.domain.model.aggregates;


import com.agroapp.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Field Aggregate Root
 * Main entity in the Plants bounded context
 */
@Entity
@Getter
public class Field extends AuditableAbstractAggregateRoot<Field> {

    @Column(nullable = false)
    private Long userId;

    private String imageUrl;
    private String name;
    private String location;
    private String fieldSize;

    public Field() {
    }

    public Field(Long userId, String imageUrl, String name, String location, String fieldSize) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.location = location;
        this.fieldSize = fieldSize;
    }
}

