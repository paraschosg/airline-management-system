package com.airline.management.service;

import com.airline.management.dto.UpdateFlightRequest;
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

    public Flight findByFlightNumber(String flightNumber) {

        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    public Flight updateFlight(Long id, UpdateFlightRequest request) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setAirplane(request.getAirplane());
        flight.setFlightDate(request.getFlightDate());
        flight.setFlightTime(request.getFlightTime());

        flight.setTotalSeats(request.getTotalSeats());
        flight.setTotalRows(request.getTotalRows());
        flight.setSeatsPerRow(request.getSeatsPerRow());

        flight.setBusinessRows(request.getBusinessRows());

        flight.setStatus(request.getStatus());

        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flightRepository.delete(flight);
    }

    public Flight changeStatus(Long id, String status) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setStatus(
                FlightStatus.valueOf(status.toUpperCase())
        );

        return flightRepository.save(flight);
    }
}