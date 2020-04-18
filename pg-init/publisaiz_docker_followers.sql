create table docker.followers
(
    userprofile_id uuid not null,
    followers_id   uuid not null,
    constraint followers_pkey
        primary key (userprofile_id, followers_id)
);

alter table docker.followers
    owner to korsarz;

