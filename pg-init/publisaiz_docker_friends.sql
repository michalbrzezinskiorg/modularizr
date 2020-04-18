create table docker.friends
(
    userprofile_id uuid not null,
    friends_id     uuid not null,
    constraint friends_pkey
        primary key (userprofile_id, friends_id)
);

alter table docker.friends
    owner to korsarz;

