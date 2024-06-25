package com.carSales.CarSales.controllers;

import com.carSales.CarSales.models.Car;
import com.carSales.CarSales.services.CarService;
import com.featureflag.FeatureFlagLibrary.services.FeatureService.FeatureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@AllArgsConstructor
public class carController {
    private CarService carService;

    private FeatureService featureService;
    @PostMapping("/{accountId}")
    public String createCar(@RequestBody Car car, @PathVariable String accountId){
        if (featureService.isFeatureEnabled("createCar",accountId).isPresent()) {
            boolean isEnabled = featureService.isFeatureEnabled("createCar",accountId).get();
            if (isEnabled) {
                carService.createCar(car);
                return "car created";
            } else {
                return "feature not available!";
            }
        } else {
            return "failed to determine feature state";
        }
    }
    @GetMapping("/{id}")
    public Car getCar(@PathVariable long id, @RequestParam String accountId) {
        if (featureService.isFeatureEnabled("getCar", accountId).isPresent()) {
            boolean isEnabled = featureService.isFeatureEnabled("getCar", accountId).get();
            if (isEnabled) {
                return carService.getCar(id);
            } else {
                throw new RuntimeException("feature not available!");
            }
        } else {
            throw new RuntimeException("failed to determine feature state");
        }
    }

    @GetMapping("/")
    public List<Car> getAllCars(@RequestParam String accountId) {
        if (featureService.isFeatureEnabled("getAllCars", accountId).isPresent()) {
            boolean isEnabled = featureService.isFeatureEnabled("getAllCars", accountId).get();
            if (isEnabled) {
                return carService.getAllCars();
            } else {
                throw new RuntimeException("feature not available!");
            }
        } else {
            throw new RuntimeException("failed to determine feature state");
        }
    }

    @PutMapping("/{id}")
    public Car updateCar(@RequestBody Car updatedCar, @PathVariable long id, @RequestParam String accountId) {
        if (featureService.isFeatureEnabled("updateCar", accountId).isPresent()) {
            boolean isEnabled = featureService.isFeatureEnabled("updateCar", accountId).get();
            if (isEnabled) {
                return carService.editCar(updatedCar, id);
            } else {
                throw new RuntimeException("feature not available!");
            }
        } else {
            throw new RuntimeException("failed to determine feature state");
        }
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable long id, @RequestParam String accountId) {
        if (featureService.isFeatureEnabled("deleteCar", accountId).isPresent()) {
            boolean isEnabled = featureService.isFeatureEnabled("deleteCar", accountId).get();
            if (isEnabled) {
                carService.deleteCar(id);
                return "deleted";
            } else {
                return "feature not available!";
            }
        } else {
            return "failed to determine feature state";
        }
    }
}
