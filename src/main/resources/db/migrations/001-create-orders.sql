create table basket
(
    id              serial primary key not null,
    ext_id          text unique        not null,
    basket_date_utc timestamp          not null,
    basket_status   text               not null,
    buyer_name      text               not null,
    payment_method  text               not null,
    product         text               not null
);

create table client_request
(
    id     serial primary key not null,
    ext_id text unique        not null,
    name   varchar(255),
    time   timestamp
);

create table outbox
(
    id              serial not null,
    content         varchar(1000),
    creation_time   timestamp,
    event_id        uuid,
    event_state     varchar(255),
    event_type_name varchar(255),
    times_sent      integer,
    topic           varchar(255)
);

create table shedlock
(
    name       varchar(64)  not null,
    lock_until timestamp    not null,
    locked_at  timestamp    not null,
    locked_by  varchar(255) not null,
    primary key (name)
);
