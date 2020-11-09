package com.decentralizer.spreadr.modules.appconfig;

import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AppconfigPostgresPort {

    Mono<User> save(User user);

    Flux<Controller> findAllControllers();

    void addNewControllerToDatabase(Controller controller);

    Mono<User> findUserByLogin(String login);

    Flux<Role> findRolesByUser(UUID userId);

    void removeControllerFromDatabase(Controller controller);

    Mono<Controller> findControllerById(String id);

    boolean existsControllerById(Controller controller);

    Flux<Controller> findControllersByGroupsOfUser(UUID userId);

    Flux<Controller> findControllersByPermissionFor(UUID userId);
}
