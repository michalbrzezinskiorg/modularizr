package com.decentralizer.spreadr.database.postgres.entities;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Data
@Table(name = "user")
public class UserEntity {

    @Id
    @Type(type = "pg-uuid")
    private UUID id;
    @Size(min = 3, max = 30)
    private String name;
    @Size(min = 3, max = 30)
    private String surname;
    private String passwordEncrypted;
    @NotNull
    @Size(min = 3, max = 30)
    @Email
    @Column(unique = true)
    private String login;
    @NotNull
    private Boolean active = false;
    private ZonedDateTime created;
    private ZonedDateTime lastLogged;
    private ZonedDateTime activated;
    private ZonedDateTime passwordChanged;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy")
    private Set<PermissionEntity> hadCreatedPrermissions = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "permissionFor")
    private Set<PermissionEntity> permissions = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<RoleEntity> roles = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, targetEntity = UserProfileEntity.class, mappedBy = "user", cascade = CascadeType.ALL)
    private
    Set<UserProfileEntity> profiles = new HashSet<>();
    @Version
    private Timestamp version;

}