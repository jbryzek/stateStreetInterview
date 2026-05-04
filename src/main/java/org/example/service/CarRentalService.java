package org.example.service;

import org.example.exception.CarNotAvailableException;
import org.example.model.Car;
import org.example.model.CarType;
import org.example.model.Reservation;
import org.example.repository.CarRepository;
import org.example.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarRentalService {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public CarRentalService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public Car addCar(CarType carType) {
        Car car = new Car(carType);
        carRepository.save(car);
        return car;
    }

    public Reservation reserveCar(CarType carType, LocalDateTime startDate, int numberOfDays) {
        if (numberOfDays <= 0) {
            throw new IllegalArgumentException("Number of days must be positive");
        }
        LocalDateTime endDate = startDate.plusDays(numberOfDays);
        Car availableCar = findAvailableCarForPeriod(carType, startDate, endDate);
        if (availableCar == null) {
            throw new CarNotAvailableException("No " + carType + " available for requested period");
        }

        Reservation reservation = new Reservation(availableCar.getId(), carType, startDate, numberOfDays);
        reservationRepository.save(reservation);
        return reservation;
    }

    private Car findAvailableCarForPeriod(CarType carType, LocalDateTime startDate, LocalDateTime endDate) {
        List<Car> carsOfType = carRepository.findByType(carType);
        return carsOfType.stream()
                .filter(car -> isCarAvailableForPeriod(car, startDate, endDate))
                .findFirst()
                .orElse(null);
    }

    private boolean isCarAvailableForPeriod(Car car, LocalDateTime startDate, LocalDateTime endDate) {
        List<Reservation> overlapping = reservationRepository.findOverlappingReservations(car.getId(), startDate, endDate);
        return overlapping.isEmpty();
    }
}
