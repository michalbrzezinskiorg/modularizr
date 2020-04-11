package com.decentralizer.spreadr.modules.appconfig;

import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Permission;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AppconfigPostgresPort {

    User save(User user);

    Set<Controller> findAllControllers();

    void addNewControllerToDatabase(Controller controller);

    User getUserByLogin(String login);

    List<Role> findRolesByUser(UUID userId);

    List<Permission> findByPermissionFor(UUID userId);

    void removeControllerFromDatabase(Controller controller);

    Controller findControllerById(String id);

    boolean existsControllerById(Controller controller);
}
