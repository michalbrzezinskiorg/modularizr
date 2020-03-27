package com.decentralizer.spreadr.apiGateway.security;

import com.decentralizer.spreadr.apiGateway.domain.PermissionGatewayDTO;
import com.decentralizer.spreadr.apiGateway.domain.RoleGatewayDTO;
import com.decentralizer.spreadr.apiGateway.domain.UserGatewayDTO;
import com.decentralizer.spreadr.modules.appconfig.AppconfigController;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Component
@RequiredArgsConstructor
class LoginClient {

    // todo: switch this implementation to some sort of http call

    private final AppconfigController appconfigController;
    private final ModelMapper modelMapper;

    public UserGatewayDTO getUserByLogin(String login) {
        User userByLogin = appconfigController.getUserByLogin(login, INSTANCE_ID);
        return modelMapper.map(userByLogin, UserGatewayDTO.class);
    }

    public List<PermissionGatewayDTO> findByPermissionFor(UserGatewayDTO user) {
        return appconfigController.findByPermissionFor(user.getId(), INSTANCE_ID)
                .stream()
                .map(a -> modelMapper.map(a, PermissionGatewayDTO.class))
                .collect(Collectors.toList());
    }

    public List<RoleGatewayDTO> findRolesByUser(UserGatewayDTO user) {
        return appconfigController.findRolesByUser(user.getId(), INSTANCE_ID)
                .stream()
                .map(a -> modelMapper.map(a, RoleGatewayDTO.class))
                .collect(Collectors.toList());
    }
}
