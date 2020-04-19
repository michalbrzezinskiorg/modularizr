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
import java.util.Set;
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
        Mono<List<Set<ControllerGatewayDTO>>> controllersFromRoles = getControllersFromRoles(roles);
        Mono<List<Set<ControllerGatewayDTO>>> controllersFromPermissions = getControllersFromPermissions(permissions);
        Flux<List<ControllerGatewayDTO>> controllers = getControllersList(controllersFromRoles, controllersFromPermissions);
        Flux<UserDetails> r = joinStreams(user, controllers);
        return Mono.from(r);
    }

    private Mono<List<Set<ControllerGatewayDTO>>> getControllersFromRoles(Mono<Flux<RoleGatewayDTO>> roles) {
        return roles.flatMap(Flux::collectList)
                .map(a -> a.stream().map(RoleGatewayDTO::getController)
                        .collect(Collectors.toList()));
    }

    private Mono<List<Set<ControllerGatewayDTO>>> getControllersFromPermissions(Mono<Flux<PermissionGatewayDTO>> permissions) {
        return permissions.flatMap(Flux::collectList)
                .map(a -> a.stream().map(PermissionGatewayDTO::getControllers)
                        .collect((Collectors.toList())));
    }

    private Flux<List<ControllerGatewayDTO>> getControllersList(Mono<List<Set<ControllerGatewayDTO>>> f, Mono<List<Set<ControllerGatewayDTO>>> p) {
        return Flux.concat(f, p)
                .flatMap(s -> Flux.just(s.stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())));
    }

    private Flux<UserDetails> joinStreams(Mono<UserGatewayDTO> userByLogin, Flux<List<ControllerGatewayDTO>> map) {
        return map.zipWith(userByLogin).map(this::buildUser).switchIfEmpty(Flux.defer(() -> buildUser(userByLogin)));
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