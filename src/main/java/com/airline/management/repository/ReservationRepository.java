package com.airline.management.repository;

import com.airline.management.model.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

    List<Reservation> findByFlight(Flight flight);

    List<Reservation> findByStatus(ReservationStatus status);

    List<Reservation> findByFlightAndStatus(Flight flight, ReservationStatus status);

    List<Reservation> findByUserAndStatus(User user, ReservationStatus status);

    List<Reservation> findByUserId(Long userId);

    boolean existsByFlightAndSeatRowAndSeatColumnAndStatus(
            Flight flight,
            Integer seatRow,
            Integer seatColumn,
            ReservationStatus reservationStatus);
    }