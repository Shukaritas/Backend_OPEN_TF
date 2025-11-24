package com.agroapp.platform.plants.domain.model.entities;



import com.agroapp.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * ProgressHistory Entity (1:1 relationship with Field)
 * Created automatically when a Field is created
 */
@Entity
@Getter
public class ProgressHistory extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long fieldId;

    private LocalDateTime watered;
    private LocalDateTime fertilized;
    private LocalDateTime pests;

    public ProgressHistory() {
    }

    public ProgressHistory(Long fieldId) {
        this.fieldId = fieldId;
        this.watered = LocalDateTime.now();
        this.fertilized = LocalDateTime.now();
        this.pests = LocalDateTime.now();
    }

    public ProgressHistory(Long fieldId, LocalDateTime watered, LocalDateTime fertilized, LocalDateTime pests) {
        this.fieldId = fieldId;
        this.watered = watered;
        this.fertilized = fertilized;
        this.pests = pests;
    }

    public ProgressHistory update(LocalDateTime watered, LocalDateTime fertilized, LocalDateTime pests) {
        this.watered = watered;
        this.fertilized = fertilized;
        this.pests = pests;
        return this;
    }
}

