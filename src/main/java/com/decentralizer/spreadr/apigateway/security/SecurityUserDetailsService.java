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
        Mono<UserGatewayDTO> userByLogin = client.getUserByLogin(username);
        Mono<Flux<RoleGatewayDTO>> roles = userByLogin.map(client::findRolesByUser);
        Mono<Flux<PermissionGatewayDTO>> permissions = userByLogin.map(client::findByPermissionFor);
        Mono<List<Set<ControllerGatewayDTO>>> f = roles.flatMap(r -> r.collectList())
                .map(a -> a.stream().map(b -> b.getController())
                        .collect(Collectors.toList()));
        Mono<List<Set<ControllerGatewayDTO>>> p = permissions.flatMap(r -> r.collectList())
                .map(a -> a.stream().map(b -> b.getControllers())
                        .collect((Collectors.toList())));
        Flux<List<ControllerGatewayDTO>> map = Flux.concat(f, p)
                .flatMap(s -> Flux.just(s.stream()
                        .flatMap(a -> a.stream())
                        .collect(Collectors.toList())));
        Flux<UserDetails> r = map.zipWith(userByLogin).map(u -> buildUser(u));
        return Mono.from(r);
    }

    private UserDetails buildUser(Tuple2<List<ControllerGatewayDTO>, UserGatewayDTO> u) {
        return User
                .withUsername(u.getT2().getLogin())
                .password(u.getT2().getPassword())
                .roles(getRoles(u)
                ).build();
    }

    private String[] getRoles(Tuple2<List<ControllerGatewayDTO>, UserGatewayDTO> u) {
        List<ControllerGatewayDTO> controllers = u.getT1();
        return controllers.stream()
                .map(c -> SecurityConfig.stringifyController(
                        c.getController(),
                        c.getMethod(),
                        c.getHttpMethod()))
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

}