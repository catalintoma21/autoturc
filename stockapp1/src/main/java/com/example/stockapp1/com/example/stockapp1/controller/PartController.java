package com.example.stockapp1.controller;

import com.example.stockapp1.dto.AddPartRequest;
import com.example.stockapp1.model.Part;
import com.example.stockapp1.service.PartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartController {
    private final PartService partService;
    
    public PartController(PartService partService) {
        this.partService = partService;
    }
    
    // Endpoint de adăugare a unei piese (standard sau non-standard)
    @PostMapping("/add")
    public ResponseEntity<?> addPart(@RequestBody AddPartRequest request) {
        Part part = partService.addPart(
            request.getName(),
            request.getCarModel(),
            request.getPrice(),
            request.getEuro(),
            request.getCode(),
            request.isStandard() // flag-ul "standard"
        );
        return ResponseEntity.ok(part);
    }
    
    // Nou: Endpoint pentru a obține piesele standard pentru un model dat
    @GetMapping("/default")
    public ResponseEntity<?> getDefaultParts(@RequestParam String carModel) {
        List<Part> parts = partService.getDefaultPartsByCarModel(carModel);
        return ResponseEntity.ok(parts);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePart(@PathVariable Long id) {
        partService.deletePartById(id);
        return ResponseEntity.ok("Piesa a fost ștearsă.");}
}
