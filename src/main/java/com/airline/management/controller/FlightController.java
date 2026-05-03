package com.airline.management.controller;

import com.airline.management.model.Flight;
import com.airline.management.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins = "*")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }

    @GetMapping
    public List<Flight> getFlights() {
        return flightService.getAllFlights();
    }
    
    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return flightService.findById(id);
    }
}