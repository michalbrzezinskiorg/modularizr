package com.decentralizer.spreadr.database.postgres;

import com.decentralizer.spreadr.database.postgres.tables.ControllerDBRow;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
interface ControllerRepository extends R2dbcRepository<ControllerDBRow, UUID> {

    @Query("select co.* from " +
            "              docker.controllers as co, " +
            "              docker.permissions p, " +
            "              docker.user u, " +
            "              docker.permissions_controller pc " +
            "where pc.permissions_id = p.id " +
            "  and pc.controller_id = co.id " +
            "  and p.permissionfor = u.id " +
            "  and u.id = :userId")
    Flux<ControllerDBRow> findByPermissionFor(UUID userId);

    @Query("select co.* from docker.controllers as co, " +
            "              docker.roles as r, " +
            "              docker.user_role ur, " +
            "              docker.user u, " +
            "              docker.role_controller rc " +
            "where rc.role_id = r.id " +
            "  and rc.controller_id = co.id " +
            "  and r.id = ur.role_id " +
            "  and u.id = ur.user_id " +
            "  and u.id = :userId")
    Flux<Controller> findControllersByGroupsOfUser(UUID userId);
}
