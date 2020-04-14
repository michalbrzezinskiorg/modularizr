package com.decentralizer.spreadr.apigateway.security;

import com.decentralizer.spreadr.apigateway.domain.ControllerGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.RoleGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.UserGatewayDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
class AuthenticationProviderImpl implements AuthenticationProvider {

    private static final String DISABLED = "Your user is manually disabled";
    private static final String COULD_NOT_CREATE = "COULD NOT CREATE USER";
    private static final String CONFIRM_EMAIL = "PLEASE CONFIRM YOUR EMAIL!";
    private static final String WRONG = "WRONG PASSWORD";
    private final BCryptPasswordEncoder passwordEncoder;
    private final AppConfigClient appConfigClient;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (appConfigClient == null)
            return null;
        Mono<UserGatewayDTO> user = getUser(authentication);
        authenticateUser(authentication.getCredentials().toString(), user);
        Set<Authority> authorities = new HashSet<>();
        setRoles(user, authorities);
        setPermissions(user, authorities);
        authorities.forEach(authority -> log.info("authority [{}] ", authority));
        return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString(), authorities);
    }

    private Mono<UserGatewayDTO> getUser(Authentication authentication) {
        log.info("getUser");
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        Mono<UserGatewayDTO> userByLogin = appConfigClient.getUserByLogin(login);
        return userByLogin.switchIfEmpty(Mono.defer(() -> createUserAccount(login, password)));
    }

    private Mono<UserGatewayDTO> createUserAccount(String login, String password) {
        log.info("createUserAccount");
        UserGatewayDTO userGatewayDTO = new UserGatewayDTO();
        userGatewayDTO.setLogin(login);
        userGatewayDTO.setPassword(password);
        return appConfigClient.createUserAcount(userGatewayDTO);
    }

    private void authenticateUser(String password, Mono<UserGatewayDTO> user) {
        log.info("authenticateUser [{}]", user);
        Optional<UserGatewayDTO> userGatewayDTO = user.blockOptional();
        checkIfIsEnabled(userGatewayDTO);
        if (userGatewayDTO.isEmpty()) {
            log.info(COULD_NOT_CREATE);
            throw new BadCredentialsException(COULD_NOT_CREATE);
        } else if (!userGatewayDTO.get().isEnabled()) {
            log.info(DISABLED);
            throw new BadCredentialsException(DISABLED);
        } else if (userGatewayDTO.get().getActivated() == null || userGatewayDTO.get().getActivated().isAfter(LocalDateTime.now())) {
            log.info(CONFIRM_EMAIL);
            throw new BadCredentialsException(CONFIRM_EMAIL);
        } else if (!passwordEncoder.matches(password, userGatewayDTO.get().getPassword())) {
            log.info(WRONG);
            throw new BadCredentialsException(WRONG);
        }
    }

    private void checkIfIsEnabled(Optional<UserGatewayDTO> userGatewayDTO) {
        // TODO: implement some sort of more advanced logic
        userGatewayDTO.ifPresent(u -> u.setEnabled(u.isActive()));
    }

    private void setPermissions(Mono<UserGatewayDTO> user, Set<Authority> authorities) {
        log.info("finding permissions by user [{}]", user);
        user.map(u -> appConfigClient.findByPermissionFor(u))
                .block()
                .filter(p -> p.isActive()).toStream()
                .forEach(permission ->
                        permission.getControllers()
                                .forEach(c -> addControllersToAuthorities(authorities)));
    }

    private Consumer<ControllerGatewayDTO> addControllersToAuthorities(Set<Authority> authorities) {
        log.info("addControllersToAuthorities [{}]", authorities);
        return action -> {
            log.info("addControllersToAuthorities action:[{}]", action);
            final String stringified = SecurityConfig.stringifyController(action.getController(), action.getMethod(), action.getHttpMethod());
            authorities.add(new Authority(stringified));
            log.info("added authority: [{}]", stringified);
        };
    }

    private void setRoles(Mono<UserGatewayDTO> user, Set<Authority> authorities) {
        log.info("setRoles [{}]", user);
        user.map(appConfigClient::findRolesByUser).block().toStream()
                .forEach(addRoleControllersToAuthorities(authorities));
    }

    private Consumer<RoleGatewayDTO> addRoleControllersToAuthorities(Set<Authority> authorities) {
        log.info("addRoleControllersToAuthorities [{}]", authorities);
        return roleGatewayDTO -> roleGatewayDTO.getControllerGatewayDTOS().forEach(addControllersToAuthorities(authorities));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    public static class Authority implements GrantedAuthority {
        public static final long serialVersionUID = 1L;
        private final String authority;

        Authority(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}