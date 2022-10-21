create schema if not exists cinema;
drop table if exists cinema.users;
drop table if exists cinema.authentications;
drop table if exists cinema.images;

create table if not exists cinema.users (
    id              serial primary key,
    email           text unique not null,
    firstName       text not null,
    lastName        text not null,
    phoneNumber     text not null,
    password        text not null
);

create table if not exists cinema.authentications (
    id              serial primary key,
    userId          int8 not null,
    authDate        date not null,
    authTime        time not null,
    ip              text not null,
    CONSTRAINT fk_user
        FOREIGN KEY(userId)
        REFERENCES cinema.users(id)
        ON DELETE CASCADE
);

create table if not exists cinema.images (
    id              serial primary key,
    userId          int8 not null,
    name            text not null,
    size            int8 not null,
    mime            text not null,
    CONSTRAINT fk_user
        FOREIGN KEY(userId)
            REFERENCES cinema.users(id)
            ON DELETE CASCADE
);