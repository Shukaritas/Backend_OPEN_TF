package com.agroapp.platform.plants.domain.model.entities;

import com.agroapp.platform.plants.domain.model.valueobjects.CropFieldStatus;
import com.agroapp.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * CropField Entity (1:1 relationship with Field)
 */
@Entity
@Getter
public class CropField extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long fieldId;

    private String crop;
    private String soilType;
    private String sunlight;
    private String watering;
    private LocalDateTime plantingDate;
    private LocalDateTime harvestDate;

    @Enumerated(EnumType.STRING)
    private CropFieldStatus status;

    public CropField() {
    }

    public CropField(Long fieldId, String crop, String soilType, String sunlight, String watering,
                     LocalDateTime plantingDate, LocalDateTime harvestDate, CropFieldStatus status) {
        this.fieldId = fieldId;
        this.crop = crop;
        this.soilType = soilType;
        this.sunlight = sunlight;
        this.watering = watering;
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
        this.status = status;
    }

    public CropField updateCrop(String crop) {
        this.crop = crop;
        return this;
    }
}

