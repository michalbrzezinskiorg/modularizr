package com.decentralizer.spreadr.modules.appconfig.domain;

import com.decentralizer.spreadr.database.postgres.tables.UserDBRow;
import lombok.Data;
import lombok.Singular;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Permission {
    private UUID id;
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private boolean active;
    private UserDBRow createdBy;
    private UserDBRow permissionFor;
    @Singular
    private List<Controller> controllers;
    private UUID version;
}
