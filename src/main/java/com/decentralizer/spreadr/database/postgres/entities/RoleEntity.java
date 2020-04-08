package com.decentralizer.spreadr.database.postgres.entities;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Data
public class RoleEntity {

    @Id
    @Type(type = "pg-uuid")
    private UUID id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "role_controller", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "controller_id")})
    private Set<ControllerEntity> controllers = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<UserEntity> users = new HashSet<>();
    private boolean active;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime edited;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String editedBy;
    @Version
    private Timestamp version;

}
