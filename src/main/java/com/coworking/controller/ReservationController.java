package com.coworking.controller;

import com.coworking.model.Reservation;
import com.coworking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @GetMapping("/user/{name}")
    public List<Reservation> getUserReservations(@PathVariable String name) {
        return reservationService.findByCustomerName(name);
    }
}