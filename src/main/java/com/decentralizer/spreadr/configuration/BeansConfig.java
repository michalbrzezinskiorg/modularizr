package com.decentralizer.spreadr.configuration;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.decentralizer.spreadr.SpreadrApplication.INSTANCE_ID;

@Slf4j
@Configuration
@EnableScheduling
@EnableR2dbcRepositories
class BeansConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().defaultHeader("instance", INSTANCE_ID).filter(filter()).build();
    }

    @Bean
    public Converter<ZonedDateTime, LocalDateTime> zonedDateTime2LocalDateTimeConverter() {
        return new Converter<>() {
            @Override
            public LocalDateTime convert(ZonedDateTime source) {
                return source.toLocalDateTime();
            }
        };
    }

    @Bean
    public Converter<LocalDateTime, ZonedDateTime> localDateTime2ZonedDateTimeConverter() {
        return new Converter<>() {
            @Override
            public ZonedDateTime convert(LocalDateTime source) {
                return source.atZone(ZoneId.systemDefault());
            }
        };
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
