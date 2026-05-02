package com.airline.management.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateFlightRequest {

    private String flightNumber;
    private String airplane;

    private LocalDate flightDate;
    private LocalTime flightTime;

    private int totalSeats;
    private int rows;
    private int seatsPerRow;
    private int businessRows;

    public CreateFlightRequest() {}

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
}