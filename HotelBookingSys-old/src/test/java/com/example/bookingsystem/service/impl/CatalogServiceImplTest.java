package com.example.bookingsystem.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookingsystem.domain.Booking;
import com.example.bookingsystem.domain.Catalog;
import com.example.bookingsystem.domain.Payment;
import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.domain.catalog.Meal;
import com.example.bookingsystem.repository.CatalogRepository;
import com.example.bookingsystem.service.catalog.FacilityService;
import com.example.bookingsystem.service.catalog.MealService;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class CatalogServiceImplTest {
    @Mock
    private CatalogRepository catalogRepository;

    @InjectMocks
    private CatalogServiceImpl catalogServiceImpl;

    @Test
    void testSaveCatalog_enteredDetailsAreEitherNullOrEmpty() {
        ArrayList<String> facilitiesName = new ArrayList<>();

        Exception ex = assertThrows(ResponseStatusException.class, () -> catalogServiceImpl.saveCatalog(facilitiesName, new ArrayList<>()));
        String expectedMessage = "entered details are either null or empty";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllCatalog_noCatalogFound() {
        when(catalogRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> catalogServiceImpl.getAllCatalog());
        String expectedMessage = "No catalog found";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(catalogRepository).findAll();
    }

    @Test
    void testGetAllCatalog_catalogFound() {
        Catalog catalog = new Catalog();
        catalog.setBooking(new Booking());
        catalog.setCatalogFacilities(new ArrayList<>());
        catalog.setCatalogMeals(new ArrayList<>());
        catalog.setId(123L);

        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(catalog);
        when(this.catalogRepository.findAll()).thenReturn(catalogList);

        List<Catalog> actualAllCatalog = catalogServiceImpl.getAllCatalog();
        assertSame(catalogList, actualAllCatalog);
        assertEquals(1, actualAllCatalog.size());
        verify(catalogRepository).findAll();
    }
}

