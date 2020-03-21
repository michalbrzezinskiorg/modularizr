package com.decentralizer.spreadr.modules.appconfig;

import com.decentralizer.spreadr.configuration.ApplicationEventsPublisher;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Permission;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import com.decentralizer.spreadr.modules.appconfig.events.NewControllerFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public
class AppconfigService {

    private final AppconfigPostgresPort appconfigPostgresPort;
    private final ApplicationEventsPublisher applicationEventsPublisher;

    public Set<Controller> findAllControllers() {
        return appconfigPostgresPort.findAllControllers();
    }

    public void addNewControllerToDatabase(Controller controller) {
        applicationEventsPublisher.publish(new NewControllerFound(controller, false));
    }

    public User getUserByLogin(String login) {
        return appconfigPostgresPort.getUserByLogin(login);
    }

    public List<Role> findRolesByUser(@PathVariable("id") UUID userId) {
        return appconfigPostgresPort.findRolesByUser(userId);
    }

    public List<Permission> findByPermissionFor(@PathVariable("id") UUID userId) {
        return appconfigPostgresPort.findByPermissionFor(userId);
    }
}
