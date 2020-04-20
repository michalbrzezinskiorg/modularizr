package com.decentralizer.spreadr.apigateway.security;

import com.decentralizer.spreadr.apigateway.domain.ControllerGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.PermissionGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.RoleGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.UserGatewayDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
class SecurityUserDetailsService implements ReactiveUserDetailsService {

    private final SecurityAppConfigClient client;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<UserGatewayDTO> user = client.getUserByLogin(username);
        Mono<Flux<RoleGatewayDTO>> roles = user.map(client::findRolesByUser);
        Mono<Flux<PermissionGatewayDTO>> permissions = user.map(client::findByPermissionFor);
        Flux<ControllerGatewayDTO> controllersFromRoles = getControllersFromRoles(roles);
        Flux<ControllerGatewayDTO> controllersFromPermissions = getControllersFromPermissions(permissions);
        Flux<ControllerGatewayDTO> controllers = Flux.concat(controllersFromRoles, controllersFromPermissions);
        return joinStreams(user, controllers);
    }

    private Flux<ControllerGatewayDTO> getControllersFromRoles(Mono<Flux<RoleGatewayDTO>> roles) {
        return roles.flatMap(Flux::collectList)
                .map(a -> a.stream()
                        .map(RoleGatewayDTO::getController)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()))
                .flatMapMany(Flux::fromIterable);
    }

    private Flux<ControllerGatewayDTO> getControllersFromPermissions(Mono<Flux<PermissionGatewayDTO>> permissions) {
        return permissions.flatMap(Flux::collectList)
                .map(a -> a.stream()
                        .map(PermissionGatewayDTO::getControllers)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()))
                .flatMapMany(Flux::fromIterable);
    }

    private Mono<UserDetails> joinStreams(Mono<UserGatewayDTO> userByLogin, Flux<ControllerGatewayDTO> map) {
        return map.collectList()
                .zipWith(userByLogin)
                .map(this::buildUser)
                .switchIfEmpty(Mono.defer(() -> buildUser(userByLogin)));
    }

    private Mono<UserDetails> buildUser(Mono<UserGatewayDTO> userGatewayDTOMono) {
        return userGatewayDTOMono.map(u -> User
                .withUsername(u.getLogin())
                .password(u.getPassword())
                .build());
    }

    private UserDetails buildUser(Tuple2<List<ControllerGatewayDTO>, UserGatewayDTO> u) {
        return User
                .withUsername(u.getT2().getLogin())
                .password(u.getT2().getPassword())
                .roles(getRoles(u))
                .build();
    }

    private String[] getRoles(Tuple2<List<ControllerGatewayDTO>, UserGatewayDTO> u) {
        List<ControllerGatewayDTO> controllers = u.getT1();
        return controllers.stream()
                .map(c -> SecurityConfig.stringifyController(
                        c.getController(),
                        c.getMethod(),
                        c.getHttpMethod())).toArray(String[]::new);
    }

}