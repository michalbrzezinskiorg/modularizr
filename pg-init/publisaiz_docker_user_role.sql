create table docker.user_role
(
    user_id uuid not null,
    role_id uuid not null,
    constraint user_role_pkey
        primary key (role_id, user_id)
);

alter table docker.user_role
    owner to korsarz;

