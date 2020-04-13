package com.decentralizer.spreadr.modules.appconfig.domain;

import com.decentralizer.spreadr.database.postgres.tables.UserProfileDBRow;
import lombok.Data;

import java.time.ZonedDateTime;
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
    private Boolean active = false;
    private ZonedDateTime created;
    private ZonedDateTime lastLogged;
    private ZonedDateTime activated;
    private ZonedDateTime passwordChanged;
    private Set<Permission> hadCreatedPrermissions = new HashSet<>();
    private Set<Permission> permissions = new HashSet<>();
    private Set<Role> roles = new HashSet<>();
}
