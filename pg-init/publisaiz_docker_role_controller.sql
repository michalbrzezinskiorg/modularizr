create table docker.role_controller
(
    controller_id uuid not null,
    role_id       uuid not null,
    constraint role_controller_pkey
        primary key (role_id, controller_id)
);

alter table docker.role_controller
    owner to korsarz;

