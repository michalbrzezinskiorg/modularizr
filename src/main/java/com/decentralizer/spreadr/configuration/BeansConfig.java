package com.decentralizer.spreadr.configuration;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Slf4j
@Configuration
@EnableScheduling
class BeansConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().defaultHeader("instance", INSTANCE_ID).filter(filter()).build();
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private ExchangeFilterFunction filter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            clientRequest
                    .headers()
                    .forEach((name, values) ->
                            values.forEach(value ->
                                    log.info("sending request with header ::: {} : [{}]", name, value)));
            return Mono.just(clientRequest);
        });
    }
}
