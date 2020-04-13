package com.decentralizer.spreadr.database.postgres.tables;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@Table("controllers")
public class ControllerDBRow  implements Persistable<UUID> {

    @Id
    private UUID id;
    private boolean active;
    private String controller;
    @Column("httpmethod")
    private String httpMethod;
    private String method;
    private Timestamp version;


    @Override
    public boolean isNew() {
        return version == null;
    }
}
