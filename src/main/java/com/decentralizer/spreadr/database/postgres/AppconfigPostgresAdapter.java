package com.decentralizer.spreadr.database.postgres;

import com.decentralizer.spreadr.database.postgres.tables.ControllerDBRow;
import com.decentralizer.spreadr.database.postgres.tables.UserDBRow;
import com.decentralizer.spreadr.modules.appconfig.AppconfigPostgresPort;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Permission;
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

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
class AppconfigPostgresAdapter implements AppconfigPostgresPort {

    private final UserRepository userRepository;
    private final ControllerRepository controllerRepository;
    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Mono<User> save(User user) {
        UserDBRow entity = modelMapper.map(user, UserDBRow.class);
        entity.setPasswordEncrypted(passwordEncoder.encode(user.getPassword()));
        entity.setPasswordChanged(ZonedDateTime.now());
        entity.setId(UUID.nameUUIDFromBytes(entity.getLogin().getBytes()));
        log.info("save(User user) saving [{}]", entity);
        Mono<UserDBRow> save = userRepository.save(entity);
        save.subscribe(s -> log.info("save(User user) saved [{}]", s));
        Mono<User> map = save.map(a -> modelMapper.map(a, User.class));
        map.subscribe(s -> log.info("saved [{}]", s));
        return map;
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
        return userRepository.getByLogin(login).map(a -> modelMapper.map(a, User.class));
    }

    @Override
    public Flux<Role> findRolesByUser(UUID userId) {
        return rolesRepository.findByUsers_id(userId);
    }

    @Override
    public Flux<Permission> findByPermissionFor(UUID userId) {
        return permissionRepository.findByPermissionFor(
                userRepository.findById(userId))
                .map(a -> modelMapper.map(a, Permission.class));
    }

    @Override
    public void removeControllerFromDatabase(Controller controller) {
        controllerRepository.delete(modelMapper.map(controller, ControllerDBRow.class));
    }

    @Override
    public Mono<Controller> findControllerById(String id) {
        return controllerRepository
                .findById(UUID.nameUUIDFromBytes(id.getBytes()))
                .map(v -> modelMapper.map(v, Controller.class));
    }

    @Override
    public boolean existsControllerById(Controller controller) {
        String id = controller.getController() + controller.getHttpMethod() + controller.getMethod();
        return findControllerById(id).blockOptional().isPresent();
    }
}
