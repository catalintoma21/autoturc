package com.example.stockapp1.repository;

import com.example.stockapp1.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByCarModelIgnoreCase(String carModel);
    
    // Nou: returnează piesele pentru un model care sunt marcate ca standard
    List<Part> findByCarModelIgnoreCaseAndStandardTrue(String carModel);
}
