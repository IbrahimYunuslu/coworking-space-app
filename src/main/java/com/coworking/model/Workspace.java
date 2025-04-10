package com.coworking.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workspaces")
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;
    private double price;
    private boolean available;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    // Constructors, getters, setters
    public Workspace() {
    }

    public Workspace(String type, double price, boolean available) {
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Type: " + type + ", Price: $" + price + ", Available: " + available;
    }
}