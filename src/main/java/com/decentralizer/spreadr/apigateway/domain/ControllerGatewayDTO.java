package com.decentralizer.spreadr.apigateway.domain;

import com.decentralizer.spreadr.database.postgres.entities.PermissionEntity;
import com.decentralizer.spreadr.database.postgres.entities.RoleEntity;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ControllerGatewayDTO {
    private UUID id;
    private String controller;
    private String method;
    private String httpMethod;
    private Set<RoleEntity> roles;
    private Set<PermissionEntity> permissions;
    private boolean active;
    private UUID version;

}
