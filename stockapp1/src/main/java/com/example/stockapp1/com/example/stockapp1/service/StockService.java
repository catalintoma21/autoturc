package com.example.stockapp1.service;

import com.example.stockapp1.model.StockEntry;
import com.example.stockapp1.model.Part;
import com.example.stockapp1.repository.StockEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StockService {
    private final StockEntryRepository stockEntryRepository;
    private final PartService partService;

    public StockService(StockEntryRepository stockEntryRepository, PartService partService) {
        this.stockEntryRepository = stockEntryRepository;
        this.partService = partService;
    }

    // Metodă pentru adăugarea automată a mașinii cu piese standard (există deja)
    @Transactional
    public void addCarToStock(String carModel) throws SQLException {
        List<Part> defaultParts = partService.getDefaultPartsByCarModel(carModel);
        if (defaultParts.isEmpty()) {
            throw new SQLException("Nu există piese standard definite pentru modelul " + carModel);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = LocalDateTime.now().format(dtf);
        for (Part part : defaultParts) {
            addOrUpdateStockEntry(part.getName(), carModel, part.getPrice(), 1, part.getEuro(), part.getCode(), now);
        }
    }

    // Noua metodă pentru adăugarea manuală a unei piese cu cantitate
    @Transactional
    public void addManualStockEntry(String partName, String carModel, double price, int quantity, int euro, String code) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = LocalDateTime.now().format(dtf);
        addOrUpdateStockEntry(partName, carModel, price, quantity, euro, code, now);
    }

    // Metodă auxiliară care caută o intrare existentă și actualizează cantitatea sau creează una nouă
    private void addOrUpdateStockEntry(String partName, String carModel, double price, int quantity, int euro, String code, String now) {
        Optional<StockEntry> optionalEntry = stockEntryRepository.findFirstByPartNameIgnoreCaseAndCarModelIgnoreCaseAndPriceAndEuroAndCodeIgnoreCase(
            partName, carModel, price, euro, code);
        if(optionalEntry.isPresent()) {
            StockEntry entry = optionalEntry.get();
            entry.setQuantity(entry.getQuantity() + quantity);
            stockEntryRepository.save(entry);
        } else {
            StockEntry newEntry = new StockEntry(partName, carModel, price, quantity, now, euro, code);
            stockEntryRepository.save(newEntry);
        }
    }

    // Metodă de căutare cu grupare: dacă există intrări multiple identice, se grupează și se totalizează cantitatea.
    public List<StockEntry> searchStock(String partName, String carModel, String euro, String code) {
        List<StockEntry> allEntries = stockEntryRepository.findAll();
        Map<String, StockEntry> groupedMap = new HashMap<>();
        for (StockEntry entry : allEntries) {
            boolean matches = entry.getPartName().toLowerCase().contains(partName.toLowerCase()) &&
                    (carModel.equalsIgnoreCase("Toate") || entry.getCarModel().equalsIgnoreCase(carModel)) &&
                    (euro.equalsIgnoreCase("Toate") || Integer.toString(entry.getEuro()).equals(euro)) &&
                    (code.equalsIgnoreCase("Toate") || entry.getCode().equalsIgnoreCase(code));
            if(matches) {
                String key = entry.getPartName().toLowerCase() + "_" +
                             entry.getCarModel().toLowerCase() + "_" +
                             entry.getPrice() + "_" +
                             entry.getEuro() + "_" +
                             entry.getCode().toLowerCase();
                if(groupedMap.containsKey(key)) {
                    StockEntry groupedEntry = groupedMap.get(key);
                    groupedEntry.setQuantity(groupedEntry.getQuantity() + entry.getQuantity());
                } else {
                    // Creăm o copie a intrării pentru grupare
                    StockEntry newEntry = new StockEntry(
                        entry.getPartName(),
                        entry.getCarModel(),
                        entry.getPrice(),
                        entry.getQuantity(),
                        entry.getEntryDate(), // sau poți alege să folosești data cea mai recentă
                        entry.getEuro(),
                        entry.getCode()
                    );
                    groupedMap.put(key, newEntry);
                }
            }
        }
        return new ArrayList<>(groupedMap.values());
    }

    @Transactional
    public void sellStockEntry(String partName, String carModel, double price, int euro, String code) throws SQLException {
        // Normalizare: eliminăm spațiile și transformăm în litere mici
        String normPartName = partName.trim().toLowerCase();
        String normCarModel = carModel.trim().toLowerCase();
        String normCode = code.trim().toLowerCase();

        Optional<StockEntry> optionalEntry = stockEntryRepository.findFirstByPartNameIgnoreCaseAndCarModelIgnoreCaseAndPriceAndEuroAndCodeIgnoreCase(
                normPartName, normCarModel, price, euro, normCode
        );
        if (optionalEntry.isPresent()) {
            StockEntry entry = optionalEntry.get();
            int currentQuantity = entry.getQuantity();
            if (currentQuantity > 1) {
                entry.setQuantity(currentQuantity - 1);
                stockEntryRepository.save(entry);
                System.out.println("Vând piesă: " + normPartName + ", cantitate nouă: " + entry.getQuantity());
            } else {
                stockEntryRepository.delete(entry);
                System.out.println("Șters piesa: " + normPartName + " deoarece cantitatea a ajuns la 0");
            }
        } else {
            throw new SQLException("Intrarea nu a fost găsită pentru vânzare.");
        }
    }
}

