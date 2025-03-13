package com.example.stockapp.controller;

import com.example.stockapp.entity.StockEntry;
import com.example.stockapp.repository.StockEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockEntryController {
    @Autowired
    private StockEntryRepository stockEntryRepository;

    @GetMapping
    public List<StockEntry> getAllStockEntries() {
        return stockEntryRepository.findAll();
    }

    @PostMapping
    public StockEntry createStockEntry(@RequestBody StockEntry stockEntry) {
        return stockEntryRepository.save(stockEntry);
    }
    
    // Endpoint-uri pentru update, delete, sell etc.
}
