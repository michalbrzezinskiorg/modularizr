package com.decentralizer.spreadr.apigateway.domain;

import com.decentralizer.spreadr.database.postgres.tables.UserProfileDBRow;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserGatewayDTO {
    private
    Set<UserProfileDBRow> profiles = new HashSet<>();
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
    private Set<PermissionGatewayDTO> hadCreatedPrermissions = new HashSet<>();
    private Set<PermissionGatewayDTO> permissions = new HashSet<>();
    private Set<RoleGatewayDTO> roleGatewayDTOS = new HashSet<>();
}
