package com.decentralizer.spreadr.database.postgres.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "controllers", uniqueConstraints = @UniqueConstraint(columnNames = {"controller", "method", "httpMethod"}))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Data
@NoArgsConstructor
public class ControllerEntity {

    @Id
    @Type(type = "pg-uuid")
    private UUID id;
    private String controller;
    private String method;
    private String httpMethod;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_controller", joinColumns = {@JoinColumn(name = "controller_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<RoleEntity> roles;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "permissions_controller", joinColumns = {@JoinColumn(name = "controller_id")}, inverseJoinColumns = {@JoinColumn(name = "permissions_id")})
    private Set<PermissionEntity> permissions;
    @NonNull
    private boolean active;
    @Version
    private Timestamp version;

}
