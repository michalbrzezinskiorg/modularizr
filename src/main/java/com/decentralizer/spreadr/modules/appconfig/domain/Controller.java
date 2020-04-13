package com.decentralizer.spreadr.modules.appconfig.domain;

import com.decentralizer.spreadr.database.postgres.tables.PermissionDBRow;
import com.decentralizer.spreadr.database.postgres.tables.RoleDBRow;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class Controller {
    private UUID id;
    private String controller;
    private String method;
    private String httpMethod;
    private Set<RoleDBRow> roles;
    private Set<PermissionDBRow> permissions;
    private boolean active;
    private UUID version;

}
