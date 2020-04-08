package com.decentralizer.spreadr.apiGateway.security;

import com.decentralizer.spreadr.apiGateway.domain.PermissionGatewayDTO;
import com.decentralizer.spreadr.apiGateway.domain.RoleGatewayDTO;
import com.decentralizer.spreadr.apiGateway.domain.UserGatewayDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Component
@RequiredArgsConstructor
class LoginClient {

    public UserGatewayDTO getUserByLogin(String login) {
        return WebClient.create("application/user/" + login)
                .get()
                .header("instance", INSTANCE_ID)
                .retrieve()
                .bodyToFlux(UserGatewayDTO.class).blockFirst();
    }

    public List<PermissionGatewayDTO> findByPermissionFor(UserGatewayDTO user) {
        return WebClient.create("application/user/" + user.getId() + "/permissions")
                .get()
                .header("instance", INSTANCE_ID)
                .retrieve()
                .bodyToFlux(PermissionGatewayDTO.class).buffer().blockFirst();
    }

    public List<RoleGatewayDTO> findRolesByUser(UserGatewayDTO user) {
        return WebClient.create("application/user/" + user.getId() + "/roles")
                .get()
                .header("instance", INSTANCE_ID)
                .retrieve()
                .bodyToFlux(RoleGatewayDTO.class).buffer().blockFirst();
    }
}
