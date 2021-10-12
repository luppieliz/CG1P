package com.app.todo.business;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByBusinessName(String businessName);
    Boolean existsByBusinessName(String businessName);
}
