package com.decentralizer.spreadr.modules.appconfig.postgres;

import com.decentralizer.spreadr.modules.appconfig.postgres.entities.ControllerEntity;
import com.decentralizer.spreadr.modules.appconfig.postgres.entities.UserEntity;
import com.decentralizer.spreadr.modules.appconfig.AppconfigPostgresPort;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Permission;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppconfigPostgresAdapter implements AppconfigPostgresPort {

    private final UserRepository userRepository;
    private final ControllerRepository controllerRepository;
    private final ModelMapper modelMapper;
    private final RolesRepository rolesRepository;

    @Override
    public User save(User user) {
        return modelMapper.map(userRepository.save(modelMapper.map(user, UserEntity.class)), User.class);
    }

    @Override
    public Set<Controller> findAllControllers() {
        return controllerRepository.findAll().stream().map(c -> modelMapper.map(c, Controller.class)).collect(Collectors.toSet());
    }

    @Override
    public void addNewControllerToDatabase(Controller controller) {
        controllerRepository.save(modelMapper.map(controller, ControllerEntity.class));
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
}
