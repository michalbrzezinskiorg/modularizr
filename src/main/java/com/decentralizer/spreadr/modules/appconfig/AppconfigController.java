package com.decentralizer.spreadr.modules.appconfig;

import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.Permission;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.HeaderParam;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("application")
@RequiredArgsConstructor
public class AppconfigController {

    private final AppconfigService appconfigService;

    @GetMapping("controllers")
    public Set<Controller> findAllControllers() {
        return appconfigService.findAllControllers();
    }

    @PostMapping("controllers")
    public void addNewControllerToDatabase(Controller controller) {
        appconfigService.addNewControllerToDatabase(controller);
    }

    @GetMapping("user/{login}")
    public User getUserByLogin(@PathVariable String login, @HeaderParam("instance") String instance) {

        return appconfigService.getUserByLogin(login);
    }

    @GetMapping("user/{id}/roles")
    public List<Role> findRolesByUser(@PathVariable("id") UUID userId) {
        return appconfigService.findRolesByUser(userId);
    }

    @GetMapping("user/{id}/permissions")
    public List<Permission> findByPermissionFor(@PathVariable("id") UUID userId) {
        return appconfigService.findByPermissionFor(userId);
    }

}
