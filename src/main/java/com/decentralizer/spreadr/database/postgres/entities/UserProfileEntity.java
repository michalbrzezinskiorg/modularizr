package com.decentralizer.spreadr.database.postgres.entities;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Data
public class UserProfileEntity {

    @Id
    private UUID id;
    @ManyToOne(optional = false)
    private UserEntity user;
    private Boolean enabled = true;
    private Boolean personal = false;
    private String description;
    private String brand;
    @Column(unique = true)
    private String link;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "followers")
    private Set<UserProfileEntity> followers;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "following")
    private Set<UserProfileEntity> following;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "friends")
    private Set<UserProfileEntity> friends;
    @Version
    private Timestamp version;

}
