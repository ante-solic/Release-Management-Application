package com.carSales.CarSales.services;

import com.carSales.CarSales.models.Car;
import com.carSales.CarSales.repositories.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public String createCar(Car car){
        carRepository.save(car);

        return "Car created";
    }
    public Car getCar(long id){
        var car = carRepository.findById(id).get();

        return car;
    }

    public List<Car> getAllCars(){
        var cars = carRepository.findAll();

        return (List<Car>) cars;
    }

    public Car editCar(Car updatedCar, long id){
        var car = carRepository.findById(id).get();

        car.setMake(updatedCar.getMake());
        car.setModel(updatedCar.getModel());

        carRepository.save(car);

        return car;
    }

    public void deleteCar(long id){
        var car = carRepository.findById(id).get();

        carRepository.delete(car);
    }
}
