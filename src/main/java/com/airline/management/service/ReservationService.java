package com.airline.management.service;

import com.airline.management.dto.ReservationResponseDTO;
import com.airline.management.dto.ReservationSearchRequest;
import com.airline.management.dto.UpdateReservationRequest;
import com.airline.management.model.*;
import com.airline.management.repository.ReservationRepository;
import com.airline.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    // CREATE
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

    // CANCEL
    public Reservation cancelReservation(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setStatus(ReservationStatus.CANCELLED);

        return reservationRepository.save(reservation);
    }

    // UPDATE (only type, only if flight not STAFFED)
    public Reservation updateReservation(Long reservationId, UpdateReservationRequest request) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getFlight().getStatus() == FlightStatus.STAFFED) {
            throw new RuntimeException("Cannot update reservation when flight is STAFFED");
        }

        reservation.setType(
                ReservationType.valueOf(request.getType().toUpperCase())
        );

        reservation.setUpdatedAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    // GET (WITH USER CHECK - NO SECURITY FRAMEWORK)
    public ReservationResponseDTO getReservation(Long reservationId, Long userId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.CUSTOMER) {
            if (!reservation.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Access denied");
            }
        }

        return mapToDTO(reservation);
    }

    // LIST USER RESERVATIONS
    public List<Reservation> getUserReservations(User user) {
        return reservationRepository.findByUser(user);
    }

    // DTO MAPPER
    private ReservationResponseDTO mapToDTO(Reservation reservation) {

        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getFlight().getFlightNumber(),
                reservation.getType().name(),
                reservation.getStatus().name(),
                reservation.getSeatRow() + "-" + reservation.getSeatColumn()
        );
    }
    public List<Reservation> searchReservations(
            ReservationSearchRequest request,
            Long userId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Reservation> reservations;

        if (user.getRole() == Role.CUSTOMER) {
            reservations = reservationRepository.findByUser(user);
        } else {
            reservations = reservationRepository.findAll();
        }

        return reservations;
    }
}