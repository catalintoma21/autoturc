package com.example.stockapp1.controller;

import com.example.stockapp1.model.CarModel;
import com.example.stockapp1.service.CarModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/models")
public class CarModelController {

    private final CarModelService carModelService;

    public CarModelController(CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCarModel(@RequestParam String modelName) {
        CarModel model = carModelService.addCarModel(modelName);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCarModel(@RequestParam String modelName) {
        carModelService.deleteCarModel(modelName);
        return ResponseEntity.ok("Modelul a fost șters.");
    }

    @GetMapping("/all")
    public List<CarModel> getAllCarModels() {
        return carModelService.getAllCarModels();
    }
}
