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
                .header("instance", INSTANCE_ID)
                .exchange()
                .map(r -> r == null ? new RuntimeException() : r)
                .flatMap(m -> ((ClientResponse) m).bodyToMono(UserGatewayDTO.class))
                .retryBackoff(5, Duration.ofSeconds(1));
    }

    public Flux<PermissionGatewayDTO> findByPermissionFor(UserGatewayDTO user) {
        return webClient
                .get()
                .uri(applicationUri + "/application/user/" + user.getId() + "/permissions")
                .header("instance", INSTANCE_ID)
                .retrieve()
                .bodyToFlux(PermissionGatewayDTO.class);
    }

    public Flux<RoleGatewayDTO> findRolesByUser(UserGatewayDTO user) {
        return webClient
                .get()
                .uri(applicationUri + "/application/user/" + user.getId() + "/roles")
                .header("instance", INSTANCE_ID)
                .retrieve()
                .bodyToFlux(RoleGatewayDTO.class);
    }

    public Flux<Controller> findAllControllers(String instanceId) {
        return webClient
                .get()
                .uri(applicationUri + "/application/controllers/")
                .header("instance", INSTANCE_ID)
                .retrieve()
                .bodyToFlux(Controller.class);
    }

    public void addNewControllerToDatabase(final Controller c, final String instanceId) {
        webClient
                .post()
                .uri(applicationUri + "/application/controllers/")
                .header("instance", INSTANCE_ID)
                .bodyValue(c)
                .retrieve().toBodilessEntity()
                .doFirst(() -> log.info("addNewControllerToDatabase before [{}]", c))
                .doOnError(e -> log.error("addNewControllerToDatabase error [{}]", e.getMessage()))
                .doOnSuccess(s -> log.info("addNewControllerToDatabase success [{}]", s))
                .retryBackoff(5, Duration.ofSeconds(10))
                .delaySubscription(Duration.ofSeconds(10))
                .subscribe();
    }

    public Mono<UserGatewayDTO> createUserAcount(UserGatewayDTO userGatewayDTO) {
        return webClient
                .post()
                .uri(applicationUri + "/application/users")
                .header("instance", INSTANCE_ID)
                .bodyValue(userGatewayDTO)
                .retrieve()
                .toEntity(UserGatewayDTO.class)
                .doFirst(() -> log.info("addNewControllerToDatabase before [{}]", userGatewayDTO))
                .doOnError(e -> log.error("addNewControllerToDatabase error [{}]", e.getMessage()))
                .doOnSuccess(s -> log.info("addNewControllerToDatabase success [{}]", s))
                .retryBackoff(5, Duration.ofSeconds(10))
                .then(getUserByLogin(userGatewayDTO.getLogin()));
    }

}
