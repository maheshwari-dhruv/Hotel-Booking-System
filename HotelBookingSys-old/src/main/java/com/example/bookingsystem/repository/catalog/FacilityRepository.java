package com.example.bookingsystem.repository.catalog;

import com.example.bookingsystem.domain.catalog.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Facility findByName(String facilityName);
    void deleteByName(String facilityName);
}
