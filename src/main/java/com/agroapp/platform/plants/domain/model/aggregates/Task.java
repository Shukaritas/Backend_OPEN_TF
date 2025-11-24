package com.agroapp.platform.plants.domain.model.aggregates;

import com.agroapp.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Task Aggregate Root
 * Associated with Field
 */
@Entity
@Getter
public class Task extends AuditableAbstractAggregateRoot<Task> {

    @Column(nullable = false)
    private Long fieldId;

    private String description;
    private LocalDateTime dueDate;

    public Task() {
    }

    public Task(Long fieldId, String description, LocalDateTime dueDate) {
        this.fieldId = fieldId;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Task update(Long fieldId, String description, LocalDateTime dueDate) {
        this.fieldId = fieldId;
        this.description = description;
        this.dueDate = dueDate;
        return this;
    }
}

