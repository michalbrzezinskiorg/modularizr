package com.decentralizer.spreadr.apigateway.security;

import com.decentralizer.spreadr.apigateway.domain.ControllerGatewayDTO;
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

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
class AuthenticationProviderImpl implements ReactiveUserDetailsService {

    private final AppConfigClient appConfigClient;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<UserGatewayDTO> userByLogin = appConfigClient.getUserByLogin(username);
        Mono<Flux<RoleGatewayDTO>> roles = userByLogin.map(appConfigClient::findRolesByUser);
        return roles.flatMap(r -> r.collectList())
                .flatMap(r -> Mono.just(r.stream()
                        .map(a -> a.getControllerGatewayDTOS())
                        .flatMap(s -> s.stream())
                        .collect(Collectors.toSet()))
                        .flatMap(x -> userByLogin.zipWith(Mono.just(x)))
                        .map(u -> buildUser(u)));
    }

    private UserDetails buildUser(Tuple2<UserGatewayDTO, Set<ControllerGatewayDTO>> u) {
        return User
                .withUsername(u.getT1().getLogin())
                .password(u.getT1().getPassword())
                .roles(u.getT2().stream()
                        .map(c -> SecurityConfig
                                .stringifyController(c.getController(), c.getMethod(), c.getHttpMethod()))
                        .collect(Collectors.toList()).toArray(String[]::new)).build();
    }

}