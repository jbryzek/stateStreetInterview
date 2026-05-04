package org.example.repository;

import org.example.model.Car;
import org.example.model.CarType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class CarRepository {
    private final Map<String, Car> cars = new ConcurrentHashMap<>();

    public void save(Car car) {
        cars.put(car.getId(), car);
    }

    public List<Car> findByType(CarType carType) {
        return cars.values().stream()
                .filter(car -> car.getCarType() == carType)
                .collect(Collectors.toList());
    }
}
