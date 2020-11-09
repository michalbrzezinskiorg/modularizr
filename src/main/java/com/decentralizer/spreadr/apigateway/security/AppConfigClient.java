package com.decentralizer.spreadr.apigateway.security;

import com.decentralizer.spreadr.apigateway.domain.PermissionGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.RoleGatewayDTO;
import com.decentralizer.spreadr.apigateway.domain.UserGatewayDTO;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
class AppConfigClient {

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
                .uri(applicationUri + "/application/user/" + login)
                .exchange()
                .map(r -> r == null ? new RuntimeException() : r)
                .flatMap(m -> ((ClientResponse) m).bodyToMono(UserGatewayDTO.class))
                .retryBackoff(1, Duration.ofSeconds(1));
    }

    public Flux<PermissionGatewayDTO> findByPermissionFor(UUID userId) {
        return webClient
                .get()
                .uri(applicationUri + "/application/user/" + userId + "/controllers")
                .retrieve()
                .bodyToFlux(PermissionGatewayDTO.class);
    }

    public Flux<RoleGatewayDTO> findRolesByUser(UserGatewayDTO user) {
        return webClient
                .get()
                .uri(applicationUri + "/application/user/" + user.getId() + "/roles")
                .retrieve()
                .bodyToFlux(RoleGatewayDTO.class);
    }

    public Flux<Controller> findAllControllers() {
        return webClient
                .get()
                .uri(applicationUri + "/application/controllers/")
                .retrieve()
                .bodyToFlux(Controller.class);
    }

    public void addNewControllerToDatabase(final Controller c, final String instanceId) {
        webClient
                .post()
                .uri(applicationUri + "/application/controllers/")
                .bodyValue(c)
                .retrieve().toBodilessEntity()
                .doOnError(e -> log.error("addNewControllerToDatabase error [{}]", e.getMessage()))
                .doOnSuccess(s -> log.info("addNewControllerToDatabase success [{}]", s))
                .retryBackoff(1, Duration.ofSeconds(1))
                .delaySubscription(Duration.ofSeconds(1))
                .subscribe();
    }

    public Mono<UserGatewayDTO> createUserAcount(UserGatewayDTO userGatewayDTO) {
        return webClient
                .post()
                .uri(applicationUri + "/application/users")
                .body(BodyInserters.fromValue(userGatewayDTO))
                .retrieve()
                .toBodilessEntity()
                .doFirst(() -> log.info("addNewControllerToDatabase before [{}]", userGatewayDTO))
                .doOnError(e -> log.error("addNewControllerToDatabase error [{}]", e.getMessage()))
                .doOnSuccess(s -> log.info("addNewControllerToDatabase success [{}]", s))
                .retryBackoff(5, Duration.ofSeconds(10))
                .then(getUserByLogin(userGatewayDTO.getLogin()));
    }

}
