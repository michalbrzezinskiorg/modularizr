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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
class AppconfigPostgresAdapter implements AppconfigPostgresPort {

    private final UserRepository userRepository;
    private final ControllerRepository controllerRepository;
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
        return null;
    }

    @Override
    public void removeControllerFromDatabase(Controller controller) {
        controllerRepository.delete(modelMapper.map(controller, ControllerEntity.class));
    }

    @Override
    public Controller findControllerById(String id) {
        return modelMapper.map(controllerRepository.findById(UUID.nameUUIDFromBytes(id.getBytes())), Controller.class);
    }

    @Override
    public boolean existsControllerById(Controller controller) {
        String name = controller.getController() + controller.getHttpMethod() + controller.getMethod();
        UUID id = UUID.nameUUIDFromBytes(name.getBytes());
        return findControllerById(name) != null;
    }
}
