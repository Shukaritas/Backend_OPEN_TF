package com.agroapp.platform.plants.application.internal.commandservices;

import com.agroapp.platform.plants.domain.model.commands.CreateCropFieldCommand;
import com.agroapp.platform.plants.domain.model.commands.UpdateCropFieldCommand;
import com.agroapp.platform.plants.domain.model.entities.CropField;
import com.agroapp.platform.plants.domain.services.CropFieldCommandService;
import com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories.CropFieldRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CropFieldCommandServiceImpl implements CropFieldCommandService {

    private final CropFieldRepository cropFieldRepository;

    public CropFieldCommandServiceImpl(CropFieldRepository cropFieldRepository) {
        this.cropFieldRepository = cropFieldRepository;
    }

    @Override
    public Optional<CropField> handle(CreateCropFieldCommand command) {
        CropField cropField = new CropField(
                command.fieldId(),
                command.crop(),
                command.soilType(),
                command.sunlight(),
                command.watering(),
                command.plantingDate(),
                command.harvestDate(),
                command.status()
        );

        CropField savedCropField = cropFieldRepository.save(cropField);
        return Optional.of(savedCropField);
    }

    @Override
    public Optional<CropField> handle(UpdateCropFieldCommand command) {
        Optional<CropField> cropFieldOptional = cropFieldRepository.findById(command.cropFieldId());

        if (cropFieldOptional.isEmpty()) {
            throw new RuntimeException("CropField not found");
        }

        CropField cropField = cropFieldOptional.get();
        cropField.updateCrop(command.crop());
        CropField updatedCropField = cropFieldRepository.save(cropField);
        return Optional.of(updatedCropField);
    }
}

