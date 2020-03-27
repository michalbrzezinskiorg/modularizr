package com.decentralizer.spreadr.modules.appconfig.postgres;

import com.decentralizer.spreadr.modules.appconfig.postgres.entities.RoleEntity;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
interface RolesRepository extends JpaRepository<RoleEntity, UUID> {
    List<Role> findByUsers_id(UUID userId);
}
