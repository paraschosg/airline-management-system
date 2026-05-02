package com.airline.management.service;

import com.airline.management.model.*;
import com.airline.management.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {

        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.CREATED);

        if (reservation.getType() == ReservationType.ECONOMY) {
            reservation.setSeatRow(null);
            reservation.setSeatColumn(null);
        }

        if (reservation.getSeatRow() != null && reservation.getSeatColumn() != null) {

            boolean exists = reservationRepository
                    .existsByFlightAndSeatRowAndSeatColumn(
                            reservation.getFlight(),
                            reservation.getSeatRow(),
                            reservation.getSeatColumn()
                    );

            if (exists) {
                throw new RuntimeException("Seat already taken");
            }
        }

        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(Long id) {

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setStatus(ReservationStatus.CANCELLED);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getUserReservations(User user) {
        return reservationRepository.findByUser(user);
    }
}