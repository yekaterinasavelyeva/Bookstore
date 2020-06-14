create table if not exists book
(
    id         serial          not null        constraint book_pkey        primary key,
    title           varchar(250)     not null,
    author          varchar(250)     not null,
    genre           varchar(100)     not null,
    created_time    timestamp(6)    default now()   not null,
    updated_time    timestamp(6)    default now()   not null,
    pages_count    integer    not null,
    price      numeric(8, 2) DEFAULT 0.00
);