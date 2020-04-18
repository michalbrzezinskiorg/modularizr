create table docker.following
(
    userprofile_id uuid not null,
    following_id   uuid not null,
    constraint following_pkey
        primary key (userprofile_id, following_id)
);

alter table docker.following
    owner to korsarz;

