package com.airline.management.repository;

import com.airline.management.model.Flight;
import com.airline.management.model.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);
    
    List<Flight> findByStatus(FlightStatus status);

    List<Flight> findByFlightDateBetween(LocalDate start, LocalDate end);

    List<Flight> findByFlightNumberContainingIgnoreCase(String flightNumber);

}