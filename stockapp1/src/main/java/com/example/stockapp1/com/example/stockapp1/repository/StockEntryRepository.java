package com.example.stockapp1.repository;

import com.example.stockapp1.model.StockEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {
    Optional<StockEntry> findFirstByPartNameIgnoreCaseAndCarModelIgnoreCaseAndPriceAndEuroAndCodeIgnoreCase(
        String partName, String carModel, double price, int euro, String code);
}
