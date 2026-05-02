create table reservations
(
    id           int auto_increment
        primary key,
    customer_id  int                                                                    null,
    flight_id    int                                                                    null,
    booking_date timestamp                                  default current_timestamp() not null,
    update_date  timestamp                                  default current_timestamp() not null on update current_timestamp(),
    type         enum ('BUSINESS', 'NORMAL', 'ECONOMY')                                 null,
    seat_row     int                                                                    null,
    seat_column  int                                                                    null,
    status       enum ('CREATED', 'COMPLETED', 'CANCELLED') default 'CREATED'           null,
    constraint reservations_ibfk_1
        foreign key (customer_id) references customers (id)
            on delete cascade,
    constraint reservations_ibfk_2
        foreign key (flight_id) references flights (id)
            on delete cascade
);

create index customer_id
    on reservations (customer_id);

create index flight_id
    on reservations (flight_id);

