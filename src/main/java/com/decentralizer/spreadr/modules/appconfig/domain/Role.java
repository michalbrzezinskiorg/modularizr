package com.decentralizer.spreadr.modules.appconfig.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Role {
    private int id;
    private String name;
    private Set<Controller> controllers = new HashSet<>();
    private Set<User> users = new HashSet<>();
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime edited;
    private String createdBy;
    private String editedBy;
    private UUID version;

}

