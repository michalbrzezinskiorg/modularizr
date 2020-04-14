package com.decentralizer.spreadr.database.postgres.tables;

import lombok.Data;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("docker.roles")
public class RoleDBRow   implements Persistable<UUID> {

    @Id
    private UUID id;
    private String name;
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime edited;
    @Column("createdby")
    private String createdBy;
    @Column("editedby")
    private String editedBy;
    private Timestamp version;

    @Override
    public boolean isNew() {
        return version == null;
    }
}
