package com.decentralizer.spreadr.database.postgres;

import com.decentralizer.spreadr.database.postgres.tables.PermissionDBRow;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
@Transactional
interface PermissionRepository extends R2dbcRepository<PermissionDBRow, Long> {

    @Query("select * from docker.permissions p where p.permissionfor_id = :byId")
    Flux<PermissionDBRow> findByPermissionFor(UUID byId);
}
