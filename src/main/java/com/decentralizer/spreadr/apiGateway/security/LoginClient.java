package com.decentralizer.spreadr.apiGateway.security;

import com.decentralizer.spreadr.apiGateway.domain.ControllerGateway;
import com.decentralizer.spreadr.apiGateway.domain.PermissionGateway;
import com.decentralizer.spreadr.apiGateway.domain.RoleGateway;
import com.decentralizer.spreadr.apiGateway.domain.UserGateway;
import com.decentralizer.spreadr.modules.appconfig.AppconfigController;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Component
@RequiredArgsConstructor
class LoginClient {

    // todo: switch this implementation to some sort of http call
    private final AppconfigController appconfigController;
    private final ModelMapper modelMapper;

    public UserGateway getUserByLogin(String login) {
        User userByLogin = appconfigController.getUserByLogin(login, INSTANCE_ID);
        return modelMapper.map(userByLogin, UserGateway.class);
    }

    public List<PermissionGateway> findByPermissionFor(UserGateway user) {
        return appconfigController.findByPermissionFor(user.getId(), INSTANCE_ID)
                .stream()
                .map(a -> modelMapper.map(a, PermissionGateway.class))
                .collect(Collectors.toList());
    }

    public List<RoleGateway> findRolesByUser(UserGateway user) {
        return appconfigController.findRolesByUser(user.getId(), INSTANCE_ID)
                .stream()
                .map(a -> modelMapper.map(a, RoleGateway.class))
                .collect(Collectors.toList());
    }

    public Set<ControllerGateway> findAllControllers() {
        return appconfigController.findAllControllers(INSTANCE_ID).stream().map(
                a -> modelMapper.map(a, ControllerGateway.class)
        ).collect(Collectors.toSet());
    }

    public void saveController(ControllerGateway controllerGateway) {
        Controller map = modelMapper.map(controllerGateway, Controller.class);
        appconfigController.addNewControllerToDatabase(map, INSTANCE_ID);
    }
}
