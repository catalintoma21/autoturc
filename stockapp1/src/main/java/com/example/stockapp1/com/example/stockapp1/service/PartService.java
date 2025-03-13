package com.example.stockapp1.service;

import com.example.stockapp1.model.Part;
import com.example.stockapp1.repository.PartRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PartService {
    private final PartRepository partRepository;
    
    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }
    
    // Actualizează metoda de adăugare pentru a primi și flag-ul "standard"
    public Part addPart(String name, String carModel, double price, int euro, String code, boolean standard) {
        Part part = new Part(name, carModel, price, euro, code, standard);
        return partRepository.save(part);
    }
    
 // Adaugă această metodă în clasa PartService
    public void deletePartById(Long id) {
        partRepository.deleteById(id);
    }

    public List<Part> getPartsByCarModel(String carModel) {
        return partRepository.findByCarModelIgnoreCase(carModel);
    }
    
    // Nou: returnează doar piesele standard pentru un model
    public List<Part> getDefaultPartsByCarModel(String carModel) {
        return partRepository.findByCarModelIgnoreCaseAndStandardTrue(carModel);
    }
}
