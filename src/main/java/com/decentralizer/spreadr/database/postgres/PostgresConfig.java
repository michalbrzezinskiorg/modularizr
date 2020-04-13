package com.decentralizer.spreadr.database.postgres;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@Configuration
@EnableR2dbcRepositories(basePackages = {"com.decentralizer.spreadr.database.postgres", "com.decentralizer.spreadr.database.postgres.entities"})
class PostgresConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.datasource1.database}")
    private String database;
    @Value("${spring.datasource1.username}")
    private String username;
    @Value("${spring.datasource1.url}")
    private String url;
    @Value("${spring.datasource1.password}")
    private String password;
    @Value("${spring.datasource1.port}")
    private Integer port;
    @Value("${spring.datasource1.schema}")
    private String schema;

    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .schema(schema)
                        .port(port)
                        .host(url)
                        .database(database)
                        .username(username)
                        .password(password).build()
        );
    }
}
