package com.decentralizer.spreadr.database.postgres;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(
        basePackages = "com.decentralizer.spreadr.database.postgres.entities",
        entityManagerFactoryRef = "pgEntityManager",
        transactionManagerRef = "pgTransactionManager")
class PostgresConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties pgDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.configuration")
    public DataSource pgDataSource() {
        return pgDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "pgEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean pgEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(pgDataSource())
                .packages("com.decentralizer.spreadr.database.postgres.entities")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager pgTransactionManager(
            final @Qualifier("pgEntityManagerFactory") LocalContainerEntityManagerFactoryBean pgEntityManagerFactory) {
        return new JpaTransactionManager(pgEntityManagerFactory.getObject());
    }

}
