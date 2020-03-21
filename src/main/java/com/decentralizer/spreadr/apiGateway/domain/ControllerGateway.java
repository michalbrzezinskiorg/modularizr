package com.decentralizer.spreadr.apiGateway.domain;

import com.decentralizer.spreadr.modules.appconfig.postgres.entities.PermissionEntity;
import com.decentralizer.spreadr.modules.appconfig.postgres.entities.RoleEntity;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ControllerGateway {
    private UUID id;
    private String controller;
    private String method;
    private String httpMethod;
    private Set<RoleEntity> roles;
    private Set<PermissionEntity> permissions;
    private boolean active;
    private UUID version;

}
