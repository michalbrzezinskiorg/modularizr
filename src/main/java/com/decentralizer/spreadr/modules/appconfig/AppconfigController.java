package com.decentralizer.spreadr.modules.appconfig;

import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Permission;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@RestController
@RequestMapping("application")
@RequiredArgsConstructor
public class AppconfigController {

    private final AppconfigService appconfigService;

    @GetMapping("controllers")
    public Set<Controller> findAllControllers(@Header("instance") String instance) {
        return appconfigService.findAllControllers();
    }

    @PostMapping("controllers")
    public void addNewControllerToDatabase(Controller controller, @Header("instance") String instance) {
        internalSecurity(instance);
        appconfigService.addNewControllerToDatabase(controller);
    }

    @GetMapping("user/{login}")
    public User getUserByLogin(@PathVariable String login, @Header("instance") String instance) {
        internalSecurity(instance);
        return appconfigService.getUserByLogin(login);
    }

    @GetMapping("user/{id}/roles")
    public List<Role> findRolesByUser(@PathVariable("id") UUID userId, @Header("instance") String instance) {
        internalSecurity(instance);
        return appconfigService.findRolesByUser(userId);
    }

    @GetMapping("user/{id}/permissions")
    public List<Permission> findByPermissionFor(@PathVariable("id") UUID userId, @Header("instance") String instance) {
        internalSecurity(instance);
        return appconfigService.findByPermissionFor(userId);
    }

    private void internalSecurity(@Header("instance") String instance) {
        if (!INSTANCE_ID.equals(instance))
            throw new AuthorizationServiceException("instance id " + instance);
    }

}
