package com.example.bookingsystem.service;

import com.example.bookingsystem.domain.Catalog;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CatalogService {
    Catalog saveCatalog(List<String> facilitiesName, List<String> mealNames);
    List<Catalog> getAllCatalog();
}
