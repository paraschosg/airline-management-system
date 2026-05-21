package com.airline.management.dto;

import com.airline.management.model.FlightStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateFlightRequest {

    private String airplane;

    private LocalDate flightDate;
    private LocalTime flightTime;

    private int totalSeats;
    private int totalRows;
    private int seatsPerRow;
    private int businessRows;

    private FlightStatus status;

    public UpdateFlightRequest() {}

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    public LocalTime getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(LocalTime flightTime) {
        this.flightTime = flightTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public int getBusinessRows() {
        return businessRows;
    }

    public void setBusinessRows(int businessRows) {
        this.businessRows = businessRows;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }
}