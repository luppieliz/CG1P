package com.app.todo.industry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {
    Optional<Industry> findByName(String industryName);
    Boolean existsByName(String industryName);
}