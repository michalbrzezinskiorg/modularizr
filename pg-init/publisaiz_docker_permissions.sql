create table docker.permissions
(
    id               uuid    not null
        constraint permissions_pkey
            primary key,
    active           boolean not null,
    fromdate         timestamp,
    todate           timestamp,
    version          timestamp default current_timestamp,
    createdby_id     uuid,
    permissionfor_id uuid,
    permissionfor    uuid,
    createdby        uuid
);

alter table docker.permissions
    owner to korsarz;

