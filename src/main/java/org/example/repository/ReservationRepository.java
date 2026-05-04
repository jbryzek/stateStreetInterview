package org.example.repository;

import org.example.model.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ReservationRepository {
    private final Map<String, Reservation> reservations = new ConcurrentHashMap<>();

    public void save(Reservation reservation){
        reservations.put(reservation.getId(), reservation);
    }

    public List<Reservation> findOverlappingReservations(String id, LocalDateTime startDate, LocalDateTime endDate) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getCarId().equals(id))
                .filter( reservation -> reservation.overlapsWith(startDate, endDate))
                .collect(Collectors.toList());
    }
}
