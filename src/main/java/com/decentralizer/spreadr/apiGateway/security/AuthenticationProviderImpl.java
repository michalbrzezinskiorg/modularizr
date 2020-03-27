package com.decentralizer.spreadr.apiGateway.security;

import com.decentralizer.spreadr.apiGateway.domain.ControllerGatewayDTO;
import com.decentralizer.spreadr.apiGateway.domain.RoleGatewayDTO;
import com.decentralizer.spreadr.apiGateway.domain.UserGatewayDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@Transactional
@Component
@Slf4j
@RequiredArgsConstructor
class AuthenticationProviderImpl implements AuthenticationProvider {

    private static final String DISABLED = "Your user is manually disabled";
    private static final String COULD_NOT_CREATE = "COULD NOT CREATE USER";
    private static final String CONFIRM_EMAIL = "PLEASE CONFIRM YOUR EMAIL!";
    private static final String WRONG = "WRONG PASSWORD";
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginClient loginClient;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (loginClient == null)
            return null;
        UserGatewayDTO user = getUser(authentication);
        authenticateUser(authentication.getCredentials().toString(), user);
        Set<Authority> authorities = new HashSet<>();
        setRoles(user, authorities);
        setPermissions(user, authorities);
        authorities.forEach(authority -> log.info("authority [{}] ", authority.getAuthority()));
        return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString(), authorities);
    }

    private UserGatewayDTO getUser(Authentication authentication) {
        String login = authentication.getName();
        UserGatewayDTO user = loginClient.getUserByLogin(login);
        return user;
    }

    private void authenticateUser(String password, UserGatewayDTO user) {
        if (user == null) {
            log.info(COULD_NOT_CREATE);
            throw new BadCredentialsException(COULD_NOT_CREATE);
        } else if (!user.isEnabled()) {
            log.info(DISABLED);
            throw new BadCredentialsException(DISABLED);
        } else if (user.getActivated() == null || user.getActivated().isAfter(ZonedDateTime.now())) {
            log.info(CONFIRM_EMAIL);
            throw new BadCredentialsException(CONFIRM_EMAIL);
        } else if (!passwordEncoder.matches(password, user.getPassword())) {
            log.info(WRONG);
            throw new BadCredentialsException(WRONG);
        }
    }

    private void setPermissions(UserGatewayDTO user, Set<Authority> authorities) {
        log.info("finding permissions by user [{}]", user);
        loginClient
                .findByPermissionFor(user).stream()
                .filter(p -> p.isActive())
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

    private void setRoles(UserGatewayDTO user, Set<Authority> authorities) {
        log.info("setRoles [{}]", user);
        loginClient.findRolesByUser(user).forEach(addRoleControllersToAuthorities(authorities));
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