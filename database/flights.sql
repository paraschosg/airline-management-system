/*create table flights
(
    id            int auto_increment
        primary key,
    flight_number varchar(20)                                                                       null,
    airplane      varchar(50)                                                                       null,
    flight_date   date                                                                              null,
    flight_time   time                                                                              null,
    total_seats   int                                                                               null,
    row_count     int                                                                               null,
    seats_per_row int                                                                               null,
    business_rows int                                                                               null,
    status        enum ('CREATED', 'STAFFED', 'COMPLETED', 'CANCELLED') default 'CREATED'           null,
    created_at    timestamp                                             default current_timestamp() not null,
    updated_at    timestamp                                             default current_timestamp() not null on update current_timestamp(),
    constraint flight_number
        unique (flight_number)
);

*/