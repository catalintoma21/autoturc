package com.example.stockapp.controller;

import com.example.stockapp.entity.Part;
import com.example.stockapp.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartController {
    @Autowired
    private PartRepository partRepository;

    @GetMapping
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    @PostMapping
    public Part createPart(@RequestBody Part part) {
        return partRepository.save(part);
    }
    
    // Alte endpoint-uri (update, delete) pot fi adăugate aici
}
