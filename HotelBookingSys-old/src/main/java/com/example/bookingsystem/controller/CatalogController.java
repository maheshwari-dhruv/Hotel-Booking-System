package com.example.bookingsystem.controller;

import com.example.bookingsystem.domain.Catalog;
import com.example.bookingsystem.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @PostMapping("/save/facilities={facilityNames}/meals={mealsName}")
    public ResponseEntity<Catalog> saveCatalog(@PathVariable List<String> facilityNames, @PathVariable List<String> mealsName) {
        return ResponseEntity.ok().body(catalogService.saveCatalog(facilityNames, mealsName));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all")
    public ResponseEntity<List<Catalog>> getAllCatalog() {
        return ResponseEntity.ok().body(catalogService.getAllCatalog());
    }
}
