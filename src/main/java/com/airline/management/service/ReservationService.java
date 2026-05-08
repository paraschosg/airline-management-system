package com.airline.management.service;

import com.airline.management.dto.*;
import com.airline.management.model.*;
import com.airline.management.repository.ReservationRepository;
import com.airline.management.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import com.airline.management.repository.FlightRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository, FlightRepository flightRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
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
    public List<SeatDTO> getAvailableSeats(Long flightId, ReservationType type) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        List<Reservation> reserved = reservationRepository.findByFlight(flight);

        Set<String> takenSeats = reserved.stream()
                .filter(r -> r.getSeatRow() != null && r.getSeatColumn() != null)
                .map(r -> r.getSeatRow() + "-" + r.getSeatColumn())
                .collect(Collectors.toSet());

        List<SeatDTO> available = new ArrayList<>();

        for (int row = 1; row <= flight.getTotalRows(); row++) {

            for (int col = 1; col <= flight.getSeatsPerRow(); col++) {

                boolean isBusinessRow = row <= flight.getBusinessRows();

                // RULE 1: ECONOMY cannot pick seats
                if (type == ReservationType.ECONOMY) {
                    continue;
                }

                // RULE 2: NORMAL cannot pick business seats
                if (type == ReservationType.NORMAL && isBusinessRow) {
                    continue;
                }

                String key = row + "-" + col;

                if (!takenSeats.contains(key)) {
                    available.add(new SeatDTO(row, col));
                }
            }
        }

        return available;
    }
    public Reservation assignSeat(Long reservationId, int row, int column) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Flight flight = reservation.getFlight();

        if (flight.getStatus() == FlightStatus.STAFFED) {
            throw new RuntimeException("Flight is closed");
        }

        // check if seat is taken
        boolean taken = reservationRepository
                .existsByFlightAndSeatRowAndSeatColumn(flight, row, column);

        if (taken) {
            throw new RuntimeException("Seat already taken");
        }

        // BUSINESS RULES
        boolean isBusiness = row <= flight.getBusinessRows();

        if (reservation.getType() == ReservationType.NORMAL && isBusiness) {
            throw new RuntimeException("NORMAL cannot take business seats");
        }

        if (reservation.getType() == ReservationType.ECONOMY) {
            throw new RuntimeException("ECONOMY cannot select seat");
        }

        reservation.setSeatRow(row);
        reservation.setSeatColumn(column);

        reservation.setUpdatedAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }
    public Reservation changeSeat(Long reservationId, ChangeSeatRequest request) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Flight flight = reservation.getFlight();

        // 1. RULE: ECONOMY cannot change seat
        if (reservation.getType() == ReservationType.ECONOMY) {
            throw new RuntimeException("ECONOMY cannot change seat");
        }

        // 2. check flight status
        if (flight.getStatus() == FlightStatus.STAFFED) {
            throw new RuntimeException("Flight is closed");
        }

        int newRow = request.getNewRow();
        int newCol = request.getNewColumn();

        // 3. check if seat exists already
        boolean taken = reservationRepository
                .existsByFlightAndSeatRowAndSeatColumn(flight, newRow, newCol);

        if (taken) {
            throw new RuntimeException("Seat already taken");
        }

        // 4. BUSINESS RULES
        boolean isBusinessRow = newRow <= flight.getBusinessRows();

        if (reservation.getType() == ReservationType.NORMAL && isBusinessRow) {
            throw new RuntimeException("NORMAL cannot take business seats");
        }

        // 5. FREE OLD SEAT (just overwrite, no need delete)
        reservation.setSeatRow(null);
        reservation.setSeatColumn(null);

        reservationRepository.save(reservation); // optional intermediate save

        // 6. ASSIGN NEW SEAT
        reservation.setSeatRow(newRow);
        reservation.setSeatColumn(newCol);
        reservation.setUpdatedAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }
    public Reservation releaseSeat(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Flight flight = reservation.getFlight();

        // 1. RULE: ECONOMY cannot release seat (according to your spec rules)
        if (reservation.getType() == ReservationType.ECONOMY) {
            throw new RuntimeException("ECONOMY cannot release seat");
        }

        // 2. check if seat exists
        if (reservation.getSeatRow() == null || reservation.getSeatColumn() == null) {
            throw new RuntimeException("No seat assigned to release");
        }

        // 3. release seat
        reservation.setSeatRow(null);
        reservation.setSeatColumn(null);

        reservation.setUpdatedAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }
}