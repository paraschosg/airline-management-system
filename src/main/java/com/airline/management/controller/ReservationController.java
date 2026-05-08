package com.airline.management.controller;

import com.airline.management.dto.ReservationResponseDTO;
import com.airline.management.dto.ReservationSearchRequest;
import com.airline.management.model.Reservation;
import com.airline.management.model.User;
import com.airline.management.service.ReservationService;
import org.springframework.web.bind.annotation.*;
import com.airline.management.dto.UpdateReservationRequest;

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
}