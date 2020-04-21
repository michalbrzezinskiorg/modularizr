package com.decentralizer.spreadr.modules.appconfig;

import com.decentralizer.spreadr.configuration.ApplicationEventsPublisher;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Permission;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import com.decentralizer.spreadr.modules.appconfig.events.NewControllerFound;
import com.decentralizer.spreadr.modules.appconfig.events.UserAccountCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class AppconfigService {

    private final AppconfigPostgresPort appconfigPostgresPort;
    private final ApplicationEventsPublisher applicationEventsPublisher;

    public Flux<Controller> findAllControllers() {
        return appconfigPostgresPort.findAllControllers();
    }

    public void addNewControllerToDatabase(Controller controller) {
        boolean exists = appconfigPostgresPort.existsControllerById(controller);
        if (!exists)
            applicationEventsPublisher.publish(new NewControllerFound(controller));
        else
            log.warn("trying to persist [{}] seems to be duplicate", controller);
    }

    public Mono<User> getUserByLogin(String login) {
        return appconfigPostgresPort.findUserByLogin(login).log();
    }

    public Flux<Role> findRolesByUser(UUID userId) {
        return appconfigPostgresPort.findRolesByUser(userId);
    }

    public Flux<Permission> findByPermissionFor(@PathVariable("id") UUID userId) {
        return appconfigPostgresPort.findByPermissionFor(userId);
    }

    public void createUser(User user) {
        log.info("going to create USER [{}]", user);
        appconfigPostgresPort.findUserByLogin(user.getLogin())
                .switchIfEmpty(Mono.defer(() -> {
                    publishUserAccountCreated(new UserAccountCreated(user));
                    return Mono.empty();
                }))
                .doOnSuccess(e -> log.info("createUser: [{}] success!", e))
                .doOnError(e -> log.error("createUser: [{}]", e.getMessage()))
                .subscribe();
    }

    private void publishUserAccountCreated(UserAccountCreated e) {
        log.info("applicationEventsPublisher.publish([{}])", e);
        applicationEventsPublisher.publish(e);
    }
}
