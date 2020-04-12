package com.decentralizer.spreadr.apigateway.security;

import com.decentralizer.spreadr.apigateway.domain.PermissionGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.RoleGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.UserGatewayDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Component
@RequiredArgsConstructor
class LoginClient {

    private final WebClient webClient;

    public UserGatewayDTO getUserByLogin(String login) {
        return webClient.get()
                .uri("application/user/" + login)
                .header("instance", INSTANCE_ID)
                .retrieve()
                .bodyToFlux(UserGatewayDTO.class).blockFirst();
    }

    public List<PermissionGatewayDTO> findByPermissionFor(UserGatewayDTO user) {
        return webClient.get()
                .uri("application/user/" + user.getId() + "/permissions")
                .header("instance", INSTANCE_ID)
                .retrieve()
                .bodyToFlux(PermissionGatewayDTO.class).buffer().blockFirst();
    }

    public List<RoleGatewayDTO> findRolesByUser(UserGatewayDTO user) {
        return webClient.get()
                .uri("application/user/" + user.getId() + "/roles")
                .header("instance", INSTANCE_ID)
                .retrieve()
                .bodyToFlux(RoleGatewayDTO.class).buffer().blockFirst();
    }
}
