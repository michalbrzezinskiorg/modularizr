package com.decentralizer.spreadr.apiGateway.security;

import com.decentralizer.spreadr.apiGateway.domain.ControllerGateway;
import com.decentralizer.spreadr.apiGateway.domain.PermissionGateway;
import com.decentralizer.spreadr.apiGateway.domain.RoleGateway;
import com.decentralizer.spreadr.apiGateway.domain.UserGateway;
import com.decentralizer.spreadr.modules.appconfig.AppconfigController;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class LoginClient {

    // todo: switch this implementation to some sort of http call
    AppconfigController appconfigController;
    ModelMapper modelMapper;

    public UserGateway getUserByLogin(String login) {
        return modelMapper.map(appconfigController.getUserByLogin(login), UserGateway.class);
    }

    public List<PermissionGateway> findByPermissionFor(UserGateway user) {
        return appconfigController.findByPermissionFor(user.getId())
                .stream()
                .map(a -> modelMapper.map(a, PermissionGateway.class))
                .collect(Collectors.toList());
    }

    public List<RoleGateway> findRolesByUser(UserGateway user) {
        return appconfigController.findRolesByUser(user.getId())
                .stream()
                .map(a -> modelMapper.map(a, RoleGateway.class))
                .collect(Collectors.toList());
    }

    public Set<ControllerGateway> findAllControllers() {
        return appconfigController.findAllControllers().stream().map(
                a -> modelMapper.map(a, ControllerGateway.class)
        ).collect(Collectors.toSet());
    }

    public void saveController(ControllerGateway controllerGateway) {
        appconfigController.addNewControllerToDatabase(modelMapper.map(controllerGateway, Controller.class));
    }
}
