package org.example.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Car {
    private final String id;
    private final CarType carType;
    private boolean available;

    public Car(CarType carType) {
        this.id = UUID.randomUUID().toString();
        this.carType = carType;
        this.available = true;
    }
}
