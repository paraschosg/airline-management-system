package com.airline.management.dto;

import java.time.LocalDateTime;

public class ReservationResponse {

    private Long id;
    private Long userId;
    private Long flightId;

    private String type;
    private String status;

    private Integer seatRow;
    private Integer seatColumn;

    private LocalDateTime reservationDate;

    public ReservationResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getSeatRow() { return seatRow; }
    public void setSeatRow(Integer seatRow) { this.seatRow = seatRow; }

    public Integer getSeatColumn() { return seatColumn; }
    public void setSeatColumn(Integer seatColumn) { this.seatColumn = seatColumn; }

    public LocalDateTime getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }
}