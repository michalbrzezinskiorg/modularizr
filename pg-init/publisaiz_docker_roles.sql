create table docker.roles
(
    id        uuid    not null
        constraint roles_pkey
            primary key,
    active    boolean not null,
    created   timestamp,
    createdby varchar(255),
    edited    timestamp,
    editedby  varchar(255),
    name      varchar(255)
        constraint ukofx66keruapi6vyqpv6f2or37
            unique,
    version   timestamp default current_timestamp
);

alter table docker.roles
    owner to korsarz;

