package com.airline.management.dto;

public class ReservationResponseDTO {

    private Long id;
    private String flightNumber;
    private String type;
    private String status;
    private String seat;

    public ReservationResponseDTO(Long id, String flightNumber, String type, String status, String seat) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.type = type;
        this.status = status;
        this.seat = seat;
    }

    public Long getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getSeat() {
        return seat;
    }
}