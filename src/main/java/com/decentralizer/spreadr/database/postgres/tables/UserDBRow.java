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
@Table("docker.user")
public class UserDBRow implements Persistable<UUID> {
    @Id
    private UUID id;
    private String name;
    private String surname;
    @Column("passwordencrypted")
    private String passwordEncrypted;
    private String login;
    private boolean active;
    @Column("created")
    private LocalDateTime created;
    @Column("lastlogged")
    private LocalDateTime lastLogged;
    @Column("activated")
    private LocalDateTime activated;
    @Column("passwordchanged")
    private LocalDateTime passwordChanged;
    private Timestamp version;

    @Override
    public boolean isNew() {
        return version == null;
    }
}