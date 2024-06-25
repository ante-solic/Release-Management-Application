package com.carSales.CarSales.repositories;

import com.carSales.CarSales.models.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
