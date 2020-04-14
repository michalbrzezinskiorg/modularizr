package com.decentralizer.spreadr.database.postgres;

import com.decentralizer.spreadr.database.postgres.tables.UserDBRow;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
interface UserRepository extends R2dbcRepository<UserDBRow, UUID> {

    @Query("select * from user u where u.name ilike %:name%")
    Flux<UserDBRow> getByName(String username);

    @Query("select * from user u where u.surname ilike %:surname%")
    Flux<UserDBRow> getBySurname(String surname);

    @Query("select * from user u where u.login ilike :login and password like :password")
    Mono<UserDBRow> getByLoginAndPasswordEncrypted(String login, String password);

    @Query("select * from user u where u.login ilike :login")
    Mono<UserDBRow> getByLogin(String login);

}
