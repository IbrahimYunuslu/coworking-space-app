package com.coworking.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    @ManyToOne
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    private String customerName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public int getWorkspaceId() {
        return workspace != null ? workspace.getId() : -1;
    }

    public Reservation() {
    }

    public Reservation(int reservationId, Workspace workspace, String customerName,
            LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.reservationId = reservationId;
        this.workspace = workspace;
        this.customerName = customerName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Workspace ID: " + workspace.getId() +
                ", Customer: " + customerName + ", Date: " + date +
                ", Time: " + startTime + " - " + endTime;
    }
}