package com.decentralizer.spreadr.modules.appconfig.postgres;

import com.decentralizer.spreadr.modules.appconfig.postgres.entities.PermissionEntity;
import com.decentralizer.spreadr.modules.appconfig.postgres.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
interface PermissionRepository extends PagingAndSortingRepository<PermissionEntity, Long> {

    Set<PermissionEntity> findByPermissionFor(UserEntity u);

    Set<PermissionEntity> findAll();

    Page<PermissionEntity> findAll(Pageable pageable);
}
