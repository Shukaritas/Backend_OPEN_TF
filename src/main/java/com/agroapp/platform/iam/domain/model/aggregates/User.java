package com.agroapp.platform.iam.domain.model.aggregates;



import com.agroapp.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * User Aggregate Root
 * Represents a user in the IAM bounded context
 */
@Entity
@Getter
public class User extends AuditableAbstractAggregateRoot<User> {

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String identificator;

    public User() {
    }

    public User(String userName, String email, String password, String phoneNumber, String identificator) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.identificator = identificator;
    }

    public User updateProfile(String userName, String email, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User updatePassword(String password) {
        this.password = password;
        return this;
    }
}


