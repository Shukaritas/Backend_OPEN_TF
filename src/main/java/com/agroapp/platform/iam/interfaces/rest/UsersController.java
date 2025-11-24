package com.agroapp.platform.iam.interfaces.rest;

import com.agroapp.platform.iam.domain.model.commands.DeleteUserCommand;
import com.agroapp.platform.iam.domain.model.queries.GetUserByEmailQuery;
import com.agroapp.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.agroapp.platform.iam.domain.services.UserCommandService;
import com.agroapp.platform.iam.domain.services.UserQueryService;
import com.agroapp.platform.iam.interfaces.rest.resources.*;
import com.agroapp.platform.iam.interfaces.rest.transform.*;
import com.agroapp.platform.iam.interfaces.rest.resources.*;
import com.agroapp.platform.iam.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UsersController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpUserResource resource) {
        var command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        var user = userCommandService.handle(command);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInUserResource resource) {
        var command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var token = userCommandService.handle(command);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userQueryService.handle(new GetUserByEmailQuery(resource.email()));
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(user.get(), token.get());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id) {
        var query = new GetUserByIdQuery(id);
        var user = userQueryService.handle(query);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserResource> updateUserProfile(@PathVariable Long id, @RequestBody UpdateUserProfileResource resource) {
        var command = UpdateUserProfileCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var user = userCommandService.handle(command);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updateUserPassword(@PathVariable Long id, @RequestBody UpdateUserPasswordResource resource) {
        var command = UpdateUserPasswordCommandFromResourceAssembler.toCommandFromResource(id, resource);
        userCommandService.handle(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var command = new DeleteUserCommand(id);
        userCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}

