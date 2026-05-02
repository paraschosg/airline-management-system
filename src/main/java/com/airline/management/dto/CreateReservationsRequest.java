package com.airline.management.dto;

public class CreateReservationsRequest {

    private Long userId;
    private Long flightId;
    private String type; // BUSINESS, NORMAL, ECONOMY

    public CreateReservationsRequest() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}