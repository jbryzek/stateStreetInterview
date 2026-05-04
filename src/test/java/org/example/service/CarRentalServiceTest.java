package org.example.service;

import org.example.exception.CarNotAvailableException;
import org.example.model.CarType;
import org.example.model.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void shouldNotReserveCarNotAvailable() {
        LocalDateTime startDate = LocalDateTime.of(2026, 6, 1, 10, 0);
        int numberOfDays = 3;
        carRentalService.reserveCar(CarType.SEDAN, startDate, numberOfDays);

        assertThrows(CarNotAvailableException.class, () -> carRentalService.reserveCar(CarType.SEDAN, startDate, 1));
    }

    @Test
    void shouldReserveCarsNotOverlappingResrvations() {
        carRentalService.addCar(CarType.SEDAN);
        LocalDateTime startDateOne = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime startDateTwo = LocalDateTime.of(2026, 6, 5, 10, 0);
        int numberOfDays = 3;

        Reservation reservationOne = carRentalService.reserveCar(CarType.SEDAN, startDateOne, numberOfDays);
        Reservation reservationTwo = carRentalService.reserveCar(CarType.SEDAN, startDateTwo, numberOfDays);

        assertNotNull(reservationOne);
        assertNotNull(reservationTwo);
        assertEquals(reservationOne.getCarId(), reservationTwo.getCarId());
    }

    @Test
    void shouldThrowInvalidNumberOfDays() {
        carRentalService.addCar(CarType.SEDAN);
        LocalDateTime startDate = LocalDateTime.of(2026, 6, 1, 10, 0);

        assertThrows(IllegalArgumentException.class, () -> carRentalService.reserveCar(CarType.SEDAN, startDate, 0));
    }
}