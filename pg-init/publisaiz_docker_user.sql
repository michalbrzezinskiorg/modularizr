create table docker.user
(
    id                uuid        not null
        constraint user_pkey
            primary key,
    activated         timestamp,
    active            boolean     not null,
    created           timestamp,
    lastlogged        timestamp,
    login             varchar(30) not null
        constraint uk_ew1hvam8uwaknuaellwhqchhb
            unique,
    name              varchar(30),
    passwordchanged   timestamp,
    passwordencrypted varchar(255),
    surname           varchar(30),
    version           timestamp default current_timestamp
);

alter table docker.user
    owner to korsarz;

