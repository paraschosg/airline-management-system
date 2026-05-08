package com.airline.management.controller;

import com.airline.management.dto.*;
import com.airline.management.model.Reservation;
import com.airline.management.model.ReservationType;
import com.airline.management.model.User;
import com.airline.management.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @PutMapping("/cancel/{id}")
    public Reservation cancelReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id);
    }

    @PostMapping("/user")
    public List<Reservation> getUserReservations(@RequestBody User user) {
        return reservationService.getUserReservations(user);
    }
    @GetMapping("/{id}/{userId}")
    public ReservationResponseDTO getReservation(
            @PathVariable Long id,
            @PathVariable Long userId) {
        return reservationService.getReservation(id, userId);
    }
    @PutMapping("/{id}")
    public Reservation updateReservation(
            @PathVariable Long id,
            @RequestBody UpdateReservationRequest request
    )
    {
        return reservationService.updateReservation(id, request);
    }
    @PostMapping("/search/{userId}")
    public List<Reservation> searchReservations(
            @PathVariable Long userId,
            @RequestBody ReservationSearchRequest request
    ) {
        return reservationService.searchReservations(request, userId);
    }
    @GetMapping("/flight/{flightId}/available-seats")
    public List<SeatDTO> getAvailableSeats(
            @PathVariable Long flightId,
            @RequestParam ReservationType type
    ) {
        return reservationService.getAvailableSeats(flightId, type);
    }
    @PutMapping("/{reservationId}/seat")
    public Reservation assignSeat(
            @PathVariable Long reservationId,
            @RequestParam int row,
            @RequestParam int column
    ) {
        return reservationService.assignSeat(reservationId, row, column);
    }
    @PutMapping("/{id}/change-seat")
    public Reservation changeSeat(
            @PathVariable Long id,
            @RequestBody ChangeSeatRequest request
    ) {
        return reservationService.changeSeat(id, request);
    }
    @PutMapping("/{id}/release-seat")
    public Reservation releaseSeat(@PathVariable Long id) {
        return reservationService.releaseSeat(id);
    }
}