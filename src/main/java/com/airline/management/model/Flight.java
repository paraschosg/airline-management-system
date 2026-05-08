package com.airline.management.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String flightNumber;

    private String airplane;

    private LocalDate flightDate;
    private LocalTime flightTime;

    private int totalSeats;
    private int totalRows;
    private int seatsPerRow;
    private int businessRows;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Flight() {}

    public Long getId() { return id; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getAirplane() { return airplane; }
    public void setAirplane(String airplane) { this.airplane = airplane; }

    public LocalDate getFlightDate() { return flightDate; }
    public void setFlightDate(LocalDate flightDate) { this.flightDate = flightDate; }

    public LocalTime getFlightTime() { return flightTime; }
    public void setFlightTime(LocalTime flightTime) { this.flightTime = flightTime; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public int getTotalRows() { return totalRows; }
    public void setTotalRows(int totalRows) { this.totalRows = totalRows; }

    public int getSeatsPerRow() { return seatsPerRow; }
    public void setSeatsPerRow(int seatsPerRow) { this.seatsPerRow = seatsPerRow; }

    public int getBusinessRows() { return businessRows; }
    public void setBusinessRows(int businessRows) { this.businessRows = businessRows; }

    public FlightStatus getStatus() { return status; }
    public void setStatus(FlightStatus status) { this.status = status; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public LocalDate getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDate updatedAt) { this.updatedAt = updatedAt; }
}