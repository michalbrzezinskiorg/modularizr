package com.decentralizer.spreadr.database.postgres;

import com.decentralizer.spreadr.database.postgres.entities.ControllerEntity;
import com.decentralizer.spreadr.database.postgres.entities.UserEntity;
import com.decentralizer.spreadr.modules.appconfig.AppconfigPostgresPort;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Permission;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
class AppconfigPostgresAdapter implements AppconfigPostgresPort {

    private final UserRepository userRepository;
    private final ControllerRepository controllerRepository;
    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;
    private final RolesRepository rolesRepository;

    @Override
    public User save(User user) {
        UserEntity entity = modelMapper.map(user, UserEntity.class);
        entity.setId(UUID.nameUUIDFromBytes(entity.getLogin().getBytes()));
        return modelMapper.map(userRepository.save(entity), User.class);
    }

    @Override
    public Set<Controller> findAllControllers() {
        return controllerRepository.findAll().stream().map(c -> modelMapper.map(c, Controller.class)).collect(Collectors.toSet());
    }

    @Override
    public void addNewControllerToDatabase(Controller controller) {
        ControllerEntity entity = modelMapper.map(controller, ControllerEntity.class);
        String name = entity.getController() + entity.getHttpMethod() + entity.getMethod();
        UUID id = UUID.nameUUIDFromBytes(name.getBytes());
        entity.setId(id);
        controllerRepository.save(entity);
    }

    @Override
    public User getUserByLogin(String login) {
        return modelMapper.map(userRepository.getByLogin(login), User.class);
    }

    @Override
    public List<Role> findRolesByUser(UUID userId) {
        return rolesRepository.findByUsers_id(userId);
    }

    @Override
    public List<Permission> findByPermissionFor(UUID userId) {
        return permissionRepository.findByPermissionFor(
                userRepository.findById(userId).get())
                .stream()
                .map(a -> modelMapper.map(a, Permission.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeControllerFromDatabase(Controller controller) {
        controllerRepository.delete(modelMapper.map(controller, ControllerEntity.class));
    }

    @Override
    public Controller findControllerById(String id) {
        Optional<ControllerEntity> byId = controllerRepository.findById(UUID.nameUUIDFromBytes(id.getBytes()));
        if (byId.isEmpty())
            return null;
        return byId.map(v -> modelMapper.map(v, Controller.class)).get();
    }

    @Override
    public boolean existsControllerById(Controller controller) {
        String id = controller.getController() + controller.getHttpMethod() + controller.getMethod();
        Controller controllerById = findControllerById(id);
        boolean res = controllerById != null && controllerById.getId() != null;
        return res;
    }
}
