package com.app.todo.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, UUID> {
    Optional<Business> findByUEN(String UEN);
    Boolean existsByUEN(String UEN);
    List<Business> findByIndustry_Name(String industryName);
}