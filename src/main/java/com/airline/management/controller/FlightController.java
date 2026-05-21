package com.airline.management.controller;

import com.airline.management.model.Flight;
import com.airline.management.service.FlightService;
import org.springframework.web.bind.annotation.*;
import com.airline.management.dto.UpdateFlightRequest;

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
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }
    
    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return flightService.findById(id);
    }

    @GetMapping("/{flightNumber}")
    public Flight getFlight(@PathVariable String flightNumber) {

        return flightService.findByFlightNumber(flightNumber);
    }

    @PutMapping("/{id}")
    public Flight updateFlight(@PathVariable Long id,
                               @RequestBody UpdateFlightRequest request) {

        return flightService.updateFlight(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable Long id) {

        flightService.deleteFlight(id);

        return "Flight deleted successfully";
    }
}