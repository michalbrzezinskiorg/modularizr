package com.decentralizer.spreadr.database.postgres;

import com.decentralizer.spreadr.database.postgres.tables.ControllerDBRow;
import com.decentralizer.spreadr.database.postgres.tables.UserDBRow;
import com.decentralizer.spreadr.modules.appconfig.AppconfigPostgresPort;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
class AppconfigPostgresAdapter implements AppconfigPostgresPort {

    private final UserRepository userRepository;
    private final ControllerRepository controllerRepository;
    private final ModelMapper modelMapper;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Mono<User> save(User user) {
        UserDBRow entity = modelMapper.map(user, UserDBRow.class);
        entity.setPasswordEncrypted(passwordEncoder.encode(user.getPassword()));
        entity.setPasswordChanged(LocalDateTime.now());
        entity.setId(UUID.nameUUIDFromBytes(entity.getLogin().getBytes()));
        return userRepository.save(entity)
                .doFirst(() -> log.info("save(User user) saving [{}]", entity))
                .doOnError(e -> log.error(e.getMessage()))
                .map(a -> modelMapper.map(a, User.class));
    }

    @Override
    public Flux<Controller> findAllControllers() {
        return controllerRepository.findAll().map(c -> modelMapper.map(c, Controller.class));
    }

    @Override
    public void addNewControllerToDatabase(Controller controller) {
        ControllerDBRow entity = modelMapper.map(controller, ControllerDBRow.class);
        String name = entity.getController() + entity.getHttpMethod() + entity.getMethod();
        UUID id = UUID.nameUUIDFromBytes(name.getBytes());
        entity.setId(id);
        controllerRepository.save(entity).subscribe();
    }

    @Override
    public Mono<User> findUserByLogin(String login) {
        return userRepository.getByLogin(login)
                .retryBackoff(3, Duration.ofSeconds(1))
                .doOnError(e -> log.error(e.getMessage()))
                .map(a -> modelMapper.map(a, User.class));
    }

    @Override
    public Flux<Role> findRolesByUser(UUID userId) {
        return rolesRepository.findByUsers_id(userId);
    }

    @Override
    public Flux<Controller> findControllersByPermissionFor(UUID userId) {
        return controllerRepository.findByPermissionFor(userId)
                .map(c -> modelMapper.map(c, Controller.class));
    }

    @Override
    public void removeControllerFromDatabase(Controller controller) {
        controllerRepository.delete(modelMapper.map(controller, ControllerDBRow.class))
                .doOnError(e -> log.info(e.getMessage())).subscribe();
    }

    @Override
    public Mono<Controller> findControllerById(String id) {
        return controllerRepository
                .findById(UUID.nameUUIDFromBytes(id.getBytes()))
                .doOnError(e -> log.info(e.getMessage()))
                .map(v -> modelMapper.map(v, Controller.class));
    }

    @Override
    public boolean existsControllerById(Controller controller) {
        String id = controller.getController() + controller.getHttpMethod() + controller.getMethod();
        return findControllerById(id).blockOptional().isPresent();
    }

    @Override
    public Flux<Controller> findControllersByGroupsOfUser(UUID userId) {
        return controllerRepository.findControllersByGroupsOfUser(userId);
    }

}
