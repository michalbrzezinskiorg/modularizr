create table docker.controllers
(
    id         uuid    not null
        constraint controllers_pkey
            primary key,
    active     boolean not null,
    controller varchar(255),
    httpmethod varchar(255),
    method     varchar(255),
    version    timestamp default current_timestamp,
    constraint uk9viujdctgog788cxxtc9slum5
        unique (controller, method, httpmethod)
);

alter table docker.controllers
    owner to korsarz;

