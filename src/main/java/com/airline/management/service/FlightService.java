package com.airline.management.service;

import com.airline.management.model.Flight;
import com.airline.management.model.FlightStatus;
import com.airline.management.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight createFlight(Flight flight) {

        flight.setStatus(FlightStatus.CREATED);

        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found"));
    }
}