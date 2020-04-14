package com.decentralizer.spreadr.database.postgres.tables;

import lombok.Data;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Table("docker.user")
public class UserDBRow   implements Persistable<UUID> {
    @Id
    private UUID id;
    private String name;
    private String surname;
    @Column("passwordencrypted")
    private String passwordEncrypted;
    private String login;
    private boolean active;
    private ZonedDateTime created;
    private ZonedDateTime lastLogged;
    private ZonedDateTime activated;
    @Column("passwordchanged")
    private ZonedDateTime passwordChanged;
    private Timestamp version;

    @Override
    public boolean isNew() {
        return version == null;
    }
}