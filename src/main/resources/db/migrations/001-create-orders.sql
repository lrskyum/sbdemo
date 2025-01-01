create table customer_order
(
    id   serial primary key not null,
    ext_id uuid unique not null,
    description text not null,
    order_date_utc timestamp not null,
    order_status text not null,
    buyer_name text not null,
    buyer_email text,
    street text not null,
    zip text not null,
    country text not null,
    payment_method text not null,
    product text not null
);
