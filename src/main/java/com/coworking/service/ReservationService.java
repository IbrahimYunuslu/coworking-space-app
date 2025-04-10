package com.coworking.service;

import com.coworking.model.Reservation;
import com.coworking.model.Workspace;
import com.coworking.repository.ReservationRepository;
import com.coworking.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
            WorkspaceRepository workspaceRepository) {
        this.reservationRepository = reservationRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        Workspace workspace = reservation.getWorkspace();
        if (!workspace.isAvailable()) {
            throw new IllegalStateException("Workspace is not available");
        }

        workspace.setAvailable(false);
        workspaceRepository.save(workspace);

        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findByCustomerName(String customerName) {
        return reservationRepository.findByCustomerName(customerName);
    }

    public void cancelReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Workspace workspace = reservation.getWorkspace();
        workspace.setAvailable(true);
        workspaceRepository.save(workspace);

        reservationRepository.delete(reservation);
    }
}