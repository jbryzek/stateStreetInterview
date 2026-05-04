package org.example.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Reservation {
    private final String id;
    private final String carId;
    private final CarType carType;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final int numberOfDays;

    public Reservation(String carId, CarType carType, LocalDateTime startDate, int numberOfDays) {
        this.id = UUID.randomUUID().toString();
        this.carId = carId;
        this.carType = carType;
        this.startDate = startDate;
        this.endDate = startDate.plusDays(numberOfDays);
        this.numberOfDays = numberOfDays;
    }

    public boolean overlapsWith(LocalDateTime startDate, LocalDateTime endDate) {
        return this.startDate.isBefore(endDate) && this.endDate.isAfter(startDate);
    }
}
