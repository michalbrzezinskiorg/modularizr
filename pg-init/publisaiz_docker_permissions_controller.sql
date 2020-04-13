create table docker.permissions_controller
(
    permissions_id uuid not null,
    controller_id  uuid not null,
    constraint permissions_controller_pkey
        primary key (controller_id, permissions_id)
);

alter table docker.permissions_controller
    owner to korsarz;

