package com.decentralizer.spreadr.database.postgres.tables;

import lombok.Data;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Table("docker.userprofile")
public class UserProfileDBRow implements Persistable<UUID> {

    @Id
    private UUID id;
    private UUID user;
    private Boolean enabled = true;
    private Boolean personal = false;
    private String description;
    private String brand;
    private String link;
    private Timestamp version;

    @Override
    public boolean isNew() {
        return version == null;
    }
}
