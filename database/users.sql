create table users
(
    id       int auto_increment
        primary key,
    username varchar(50)                                       null,
    email    varchar(100)                                      null,
    password varchar(100)                                      null,
    fullname varchar(100)                                      null,
    at       varchar(20)                                       null,
    role     enum ('customer', 'flight_admin', 'system_admin') null,
    status   enum ('active', 'inactive') default 'active'      null,
    constraint at
        unique (at),
    constraint username
        unique (username)
);

