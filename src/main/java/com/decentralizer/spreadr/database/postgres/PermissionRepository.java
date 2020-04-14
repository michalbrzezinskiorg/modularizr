package com.decentralizer.spreadr.database.postgres;

import com.decentralizer.spreadr.database.postgres.tables.PermissionDBRow;
import com.decentralizer.spreadr.database.postgres.tables.UserDBRow;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Transactional
interface PermissionRepository extends R2dbcRepository<PermissionDBRow, Long> {

    @Query("select * from docker.permissions p where p.permissionfor_id = :byId.id")
    Flux<PermissionDBRow> findByPermissionFor(Mono<UserDBRow> byId);
}
