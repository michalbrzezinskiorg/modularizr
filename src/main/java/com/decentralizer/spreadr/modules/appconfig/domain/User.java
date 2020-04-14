package com.decentralizer.spreadr.modules.appconfig.domain;

import com.decentralizer.spreadr.database.postgres.tables.UserProfileDBRow;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class User {
    private
    Set<UserProfileDBRow> profiles = new HashSet<>();
    private UUID id;
    private String name;
    private String surname;
    private String password;
    private boolean enabled;
    private String login;
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime lastLogged;
    private LocalDateTime activated;
    private LocalDateTime passwordChanged;
    private Set<Permission> hadCreatedPrermissions = new HashSet<>();
    private Set<Permission> permissions = new HashSet<>();
    private Set<Role> roles = new HashSet<>();
}
