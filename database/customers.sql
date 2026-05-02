create table customers
(
    id      int auto_increment
        primary key,
    user_id int          null,
    afm     varchar(20)  null,
    address varchar(255) null,
    constraint afm
        unique (afm),
    constraint customers_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade
);

create index user_id
    on customers (user_id);

