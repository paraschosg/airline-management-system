package com.airline.management.controller;

import com.airline.management.model.Reservation;
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
}