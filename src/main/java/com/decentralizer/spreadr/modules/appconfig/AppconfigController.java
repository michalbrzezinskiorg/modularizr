package com.decentralizer.spreadr.modules.appconfig;

import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Permission;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@RestController
@RequestMapping("application")
@RequiredArgsConstructor
@Slf4j
public class AppconfigController {

    private final AppconfigService appconfigService;

    @GetMapping("controllers")
    public Flux<Controller> findAllControllers(@RequestHeader("instance") String instance) {
        internalSecurity(instance);
        return appconfigService.findAllControllers();
    }

    @PostMapping("controllers")
    public void addNewControllerToDatabase(@RequestBody Controller controller, @RequestHeader("instance") String instance) {
        internalSecurity(instance);
        appconfigService.addNewControllerToDatabase(controller);
    }

    @GetMapping("user/{login}")
    public Mono<User> getUserByLogin(@PathVariable String login, @RequestHeader("instance") String instance) {
        internalSecurity(instance);
        return appconfigService.getUserByLogin(login);
    }

    @GetMapping("user/{id}/roles")
    public Flux<Role> findRolesByUser(@PathVariable("id") UUID userId, @RequestHeader("instance") String instance) {
        internalSecurity(instance);
        return appconfigService.findRolesByUser(userId);
    }

    @GetMapping("user/{id}/permissions")
    public Flux<Permission> findByPermissionFor(@PathVariable("id") UUID userId, @RequestHeader("instance") String instance) {
        internalSecurity(instance);
        return appconfigService.findByPermissionFor(userId);
    }

    @PostMapping("users")
    public Mono<Void> createUser(@RequestBody User user, @RequestHeader("instance") String instance) {
        internalSecurity(instance);
        return appconfigService.createUser(user);
    }

    private void internalSecurity(String instance) {
        if (!INSTANCE_ID.equals(instance))
            throw new AuthorizationServiceException("incorrect instance id [" + instance + "]");
    }

}
