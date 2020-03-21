package com.decentralizer.spreadr.apiGateway.domain;

import com.decentralizer.spreadr.modules.appconfig.postgres.entities.UserProfileEntity;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserGateway {
    private
    Set<UserProfileEntity> profiles = new HashSet<>();
    private UUID id;
    private String name;
    private String surname;
    private String password;
    private String login;
    private boolean active;
    private boolean enabled;
    private ZonedDateTime created;
    private ZonedDateTime lastLogged;
    private ZonedDateTime activated;
    private ZonedDateTime passwordChanged;
    private Set<PermissionGateway> hadCreatedPrermissions = new HashSet<>();
    private Set<PermissionGateway> permissions = new HashSet<>();
    private Set<RoleGateway> roleGateways = new HashSet<>();
}
