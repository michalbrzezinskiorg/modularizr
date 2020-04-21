package com.decentralizer.spreadr.apigateway.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@Slf4j
class SecurityConfig {

    private static final String POST_USERS_INTERNAL = "/application/users";
    private static final String USERS_INTERNAL = "/application/user/**";
    private static final String CONTROLLERS_INTERNAL = "/application/controllers/";
    private static final String[] PUBLIC_PLACES = {POST_USERS_INTERNAL, CONTROLLERS_INTERNAL, USERS_INTERNAL};

    private final SpringControllersForSecurity springControllersDiscovery;

    public static String stringifyController(String getClassLevelAnnotation, String getMethodLevelAnnotation, String httpMethod) {
        String stringified = getClassLevelAnnotation.concat((getMethodLevelAnnotation.length() > 0 ? getMethodLevelAnnotation : ""));
        stringified = stringified.concat("$" + httpMethod);
        log.info("stringified controller: [{}]", stringified);
        return stringified;
    }

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity httpSecurity) {
        log.info("configure(HttpSecurity [{}])", httpSecurity);
        final List<String> publicPlacesAsList = Arrays.asList(PUBLIC_PLACES);
        List<String> nonPublicControllers = getNonPublicControllers(publicPlacesAsList);
        var expr = setHttpSecurityForPublicPlaces(httpSecurity);
        setHttpSecurityForAuthorities(expr, nonPublicControllers);
        options(expr);
        finish(expr);
        csrf(httpSecurity);
        return httpSecurity.build();
    }

    private void csrf(ServerHttpSecurity httpSecurity) {
        httpSecurity.csrf(CsrfSpec::disable);
    }

    private ArrayList<String> getNonPublicControllers(List<String> publicPlacesAsList) {
        log.info("getNonPublicControllers(List<String>  [{}]))", publicPlacesAsList);
        var result = springControllersDiscovery.getControllers().stream()
                .map(c -> stringifyController(c.getClassLevelAnnotation(), c.getMethodLevelAnnotation(), c.getHttpMethod()))
                .filter(c -> !publicPlacesAsList.contains(c)).distinct()
                .collect(Collectors.toCollection(ArrayList::new));
        publicPlacesAsList.forEach(c -> log.info("publicPlace [{}]", c));
        result.forEach(c -> log.info("nonPublicController [{}]", c));
        return result;
    }

    private ServerHttpSecurity.AuthorizeExchangeSpec
    setHttpSecurityForPublicPlaces(
            ServerHttpSecurity expr) {
        log.info("setHttpSecurityForPublicPlaces [{}]", PUBLIC_PLACES);
        return expr.authorizeExchange().pathMatchers(SecurityConfig.PUBLIC_PLACES).permitAll();
    }

    private void options(ServerHttpSecurity.AuthorizeExchangeSpec expr) {
        log.info("options");
        expr.pathMatchers(HttpMethod.OPTIONS).permitAll();
    }

    private void finish(ServerHttpSecurity.AuthorizeExchangeSpec expr) {
        expr.anyExchange().authenticated()
                .and().formLogin();
    }

    private void setHttpSecurityForAuthorities(final ServerHttpSecurity.AuthorizeExchangeSpec expr, List<String> nonPublicControllers) {
        nonPublicControllers.forEach(auth -> nonPublicController(expr, auth));
    }

    private void nonPublicController(
            ServerHttpSecurity.AuthorizeExchangeSpec expr, String auth) {
        HttpMethod httpMethod = getHttpMethodFromAuthority(auth);
        String local = auth.substring(0, auth.lastIndexOf("$"));
        log.info("setHttpSecurityForAuthorities httpMethod: [{}] local: [{}]", httpMethod, local);
        if (httpMethod != null)
            expr.pathMatchers(httpMethod, local).hasAuthority(auth);
        else
            log.error("auth: [{}]", auth);
    }

    private HttpMethod getHttpMethodFromAuthority(String auth) {
        int index = auth.lastIndexOf("$");
        HttpMethod r = null;
        if (index > 0) {
            String method = auth.substring(index + 1);
            log.info("auth [{}], HttpMethod [{}]", auth, r);
            r = HttpMethod.valueOf(method);
        }
        log.info("auth [{}], HttpMethod [{}]", auth, r);
        return r;
    }

}
