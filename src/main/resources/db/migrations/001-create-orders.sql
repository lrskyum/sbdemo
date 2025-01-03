create table customer_order
(
    id             serial primary key not null,
    ext_id         uuid unique        not null,
    description    text               not null,
    order_date_utc timestamp          not null,
    order_status   text               not null,
    buyer_name     text               not null,
    buyer_email    text,
    street         text               not null,
    zip            text               not null,
    country        text               not null,
    payment_method text               not null,
    product        text               not null
);

create table client_request
(
    id     serial primary key not null,
    ext_id uuid               not null,
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
