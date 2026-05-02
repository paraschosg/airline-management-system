package com.airline.management.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightResponse {

    private Long id;
    private String flightNumber;
    private String airplane;

    private LocalDate flightDate;
    private LocalTime flightTime;

    private int totalSeats;
    private int rows;
    private int seatsPerRow;
    private int businessRows;

    private String status;

    public FlightResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public int getRows() { return rows; }
    public void setRows(int rows) { this.rows = rows; }

    public int getSeatsPerRow() { return seatsPerRow; }
    public void setSeatsPerRow(int seatsPerRow) { this.seatsPerRow = seatsPerRow; }

    public int getBusinessRows() { return businessRows; }
    public void setBusinessRows(int businessRows) { this.businessRows = businessRows; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}