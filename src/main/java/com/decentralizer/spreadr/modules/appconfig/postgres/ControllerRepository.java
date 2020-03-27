package com.decentralizer.spreadr.modules.appconfig.postgres;

import com.decentralizer.spreadr.modules.appconfig.postgres.entities.ControllerEntity;
import com.decentralizer.spreadr.modules.appconfig.postgres.entities.PermissionEntity;
import com.decentralizer.spreadr.modules.appconfig.postgres.entities.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@Transactional
interface ControllerRepository extends JpaRepository<ControllerEntity, UUID> {

    List<ControllerEntity> findAll();

    Page<ControllerEntity> findAll(Pageable pageable);

    Set<ControllerEntity> findByRoles(RoleEntity role);

    Set<ControllerEntity> findByPermissions(PermissionEntity permission);

    ControllerEntity findByController(String controller);

    ControllerEntity findById(int id);

    @Query("select c from ControllerEntity c order by c.controller asc")
    Page<ControllerEntity> findDistinctOrderByControllerAsc(Pageable pageable);
}
