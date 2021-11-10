package com.app.todo.industry;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, UUID> {
    Optional<Industry> findByName(String industryName);
    Boolean existsByName(String industryName);
}