package org.example.service;

import org.example.model.CarType;
import org.example.model.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarRentalServiceTest {

    @Autowired
    private CarRentalService carRentalService;

    @Test
    void shouldReserveCarSuccessfully() {
        carRentalService.addCar(CarType.SEDAN);
        LocalDateTime startDate = LocalDateTime.of(2026, 6, 1, 10, 0);
        int numberOfDays = 3;

        Reservation reservation = carRentalService.reserveCar(CarType.SEDAN, startDate, numberOfDays);

        assertNotNull(reservation);
        assertEquals(CarType.SEDAN, reservation.getCarType());
        assertEquals(startDate, reservation.getStartDate());
        assertEquals(numberOfDays, reservation.getNumberOfDays());
        assertEquals(startDate.plusDays(numberOfDays), reservation.getEndDate());
    }

}