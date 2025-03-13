package com.example.stockapp1.service;

import com.example.stockapp1.model.CarModel;
import com.example.stockapp1.repository.CarModelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarModelService {

    private final CarModelRepository carModelRepository;

    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    public CarModel addCarModel(String modelName) {
        CarModel model = new CarModel(modelName);
        return carModelRepository.save(model);
    }

    public void deleteCarModel(String modelName) {
        carModelRepository.deleteByModelNameIgnoreCase(modelName);
    }

    public List<CarModel> getAllCarModels() {
        return carModelRepository.findAll();
    }
}
