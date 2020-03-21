package com.decentralizer.spreadr.modules.appconfig.postgres;

import com.decentralizer.spreadr.modules.appconfig.postgres.entities.RoleEntity;
import com.decentralizer.spreadr.modules.appconfig.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface RolesRepository extends JpaRepository<RoleEntity, UUID> {
    List<Role> findByUsers_id(UUID userId);
}
