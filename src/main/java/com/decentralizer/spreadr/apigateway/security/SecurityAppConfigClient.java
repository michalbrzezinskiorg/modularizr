package com.decentralizer.spreadr.apigateway.security;

import com.decentralizer.spreadr.apigateway.domain.PermissionGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.RoleGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.UserGatewayDTO;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Component
@RequiredArgsConstructor
@Slf4j
class SecurityAppConfigClient {

    public static final String APPLICATION_USER = "/application/user/";
    public static final String HEADER_NAME = "instance";
    public static final String APPLICATION_CONTROLLERS = "/application/controllers/";
    private final WebClient webClient;

    @Value("${app.context.url}")
    String url;
    @Value("${server.servlet.context-path}")
    String ctx;
    private String applicationUri;

    @PostConstruct
    public void setApplicationUri() {
        applicationUri = url + ctx;
    }

    public Mono<UserGatewayDTO> getUserByLogin(String login) {
        return webClient.get()
                .uri(applicationUri + APPLICATION_USER + login)
                .header(HEADER_NAME, INSTANCE_ID)
                .exchange()
                .map(r -> r == null ? new RuntimeException() : r)
                .flatMap(m -> ((ClientResponse) m).bodyToMono(UserGatewayDTO.class))
                .retryBackoff(1, Duration.ofSeconds(1));
    }

    public Flux<PermissionGatewayDTO> findByPermissionFor(UserGatewayDTO user) {
        return webClient
                .get()
                .uri(applicationUri + APPLICATION_USER + user.getId() + "/permissions")
                .header(HEADER_NAME, INSTANCE_ID)
                .retrieve()
                .bodyToFlux(PermissionGatewayDTO.class);
    }

    public Flux<RoleGatewayDTO> findRolesByUser(UserGatewayDTO user) {
        return webClient
                .get()
                .uri(applicationUri + APPLICATION_USER + user.getId() + "/roles")
                .header(HEADER_NAME, INSTANCE_ID)
                .retrieve()
                .bodyToFlux(RoleGatewayDTO.class);
    }

    public void addNewControllerToDatabase(final Controller c, final String instanceId) {
        webClient
                .post()
                .uri(applicationUri + APPLICATION_CONTROLLERS)
                .header(HEADER_NAME, INSTANCE_ID)
                .bodyValue(c)
                .retrieve().toBodilessEntity()
                .doOnError(e -> log.error("addNewControllerToDatabase error [{}]", e.getMessage()))
                .doOnSuccess(s -> log.info("addNewControllerToDatabase success [{}]", s))
                .retryBackoff(1, Duration.ofSeconds(1))
                .delaySubscription(Duration.ofSeconds(1))
                .subscribe();
    }

}
