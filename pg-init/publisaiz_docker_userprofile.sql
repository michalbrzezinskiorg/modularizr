create table docker.userprofile
(
    id          uuid not null
        constraint userprofile_pkey
            primary key,
    brand       varchar(255),
    description varchar(255),
    enabled     boolean,
    link        varchar(255)
        constraint uk_24g6y0o6kpsl7qfoytpwmdnjk
            unique,
    personal    boolean,
    version     timestamp default current_timestamp,
    user_id     uuid not null
);

alter table docker.userprofile
    owner to korsarz;

