package com.decentralizer.spreadr.apigateway.domain;

import com.decentralizer.spreadr.database.postgres.tables.UserDBRow;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class PermissionGatewayDTO {
    private long id;
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private boolean active;
    private UserDBRow createdBy;
    private UserDBRow permissionFor;
    private Set<ControllerGatewayDTO> controllers = new HashSet<>();
    private UUID version;
}
