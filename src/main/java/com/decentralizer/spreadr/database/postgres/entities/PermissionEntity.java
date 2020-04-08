package com.decentralizer.spreadr.database.postgres.entities;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "permissions")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Data
public class PermissionEntity {
    @Id
    @Type(type = "pg-uuid")
    private UUID id;
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private boolean active;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private UserEntity createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private UserEntity permissionFor;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "permissions_controller", joinColumns = {@JoinColumn(name = "permissions_id")}, inverseJoinColumns = {@JoinColumn(name = "controller_id")})
    @Fetch(FetchMode.JOIN)
    private Set<ControllerEntity> controllers = new HashSet<>();
    @Version
    private Timestamp version;
}
