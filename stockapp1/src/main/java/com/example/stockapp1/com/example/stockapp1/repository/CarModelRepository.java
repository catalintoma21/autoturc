package com.example.stockapp1.repository;

import com.example.stockapp1.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    Optional<CarModel> findByModelNameIgnoreCase(String modelName);
    void deleteByModelNameIgnoreCase(String modelName);
}
