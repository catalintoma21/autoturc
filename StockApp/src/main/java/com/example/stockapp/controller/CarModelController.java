package com.example.stockapp.controller;

import com.example.stockapp.entity.CarModel;
import com.example.stockapp.repository.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carmodels")
public class CarModelController {
    @Autowired
    private CarModelRepository carModelRepository;

    @GetMapping
    public List<CarModel> getAllCarModels() {
        return carModelRepository.findAll();
    }

    @PostMapping
    public CarModel createCarModel(@RequestBody CarModel carModel) {
        return carModelRepository.save(carModel);
    }
    
    @DeleteMapping("/{id}")
    public void deleteCarModel(@PathVariable Long id) {
        carModelRepository.deleteById(id);
    }
}
