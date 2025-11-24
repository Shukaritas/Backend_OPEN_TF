package com.agroapp.platform.iam.interfaces.rest.transform;

import com.agroapp.platform.iam.domain.model.commands.SignUpCommand;
import com.agroapp.platform.iam.interfaces.rest.resources.SignUpUserResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpUserResource resource) {
        return new SignUpCommand(
                resource.userName(),
                resource.email(),
                resource.password(),
                resource.phoneNumber(),
                resource.identificator()
        );
    }
}

