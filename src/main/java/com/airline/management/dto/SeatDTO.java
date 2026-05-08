package com.airline.management.dto;

public class SeatDTO {
    private int rows;
    private int columns;

    public SeatDTO(int row, int column) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRow() {
        return rows;
    }

    public int getColumn() {
        return columns;
    }
}