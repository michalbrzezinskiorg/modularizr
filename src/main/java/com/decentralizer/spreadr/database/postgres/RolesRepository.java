package com.decentralizer.spreadr.database.postgres;

import com.decentralizer.spreadr.database.postgres.tables.RoleDBRow;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
interface RolesRepository extends R2dbcRepository<RoleDBRow, UUID>{

    @Query("select * from roles r, users u, user_role ur where ur.user_id = u.id and r.id = ur.role_id and u.id like :userId")
    Flux<Role> findByUsers_id(UUID userId);
}
