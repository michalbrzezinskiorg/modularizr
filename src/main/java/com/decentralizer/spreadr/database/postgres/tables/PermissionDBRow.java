package com.decentralizer.spreadr.database.postgres.tables;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Table("permissions")
public class PermissionDBRow   implements Persistable<UUID> {

    @Id
    private UUID id;
    @Column("fromdate")
    private ZonedDateTime fromDate;
    @Column("todate")
    private ZonedDateTime toDate;
    private boolean active;
    private UUID createdBy;
    private UUID permissionFor;
    private Timestamp version;

    @Override
    public boolean isNew() {
        return version == null;
    }
}
