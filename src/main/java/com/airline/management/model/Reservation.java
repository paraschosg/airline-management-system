package com.airline.management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private ReservationType type;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private Integer seatRow;
    private Integer seatColumn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    public Reservation() {}

    public Long getId() {return id;}

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public ReservationType getType() { return type; }
    public void setType(ReservationType type) { this.type = type; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    public Integer getSeatRow() { return seatRow; }
    public void setSeatRow(Integer seatRow) { this.seatRow = seatRow; }

    public Integer getSeatColumn() { return seatColumn; }
    public void setSeatColumn(Integer seatColumn) { this.seatColumn = seatColumn; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }
}