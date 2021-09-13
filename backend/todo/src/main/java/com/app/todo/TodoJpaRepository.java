package com.app.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Used by TodoJpaResource.
// Spring Data JPA interface containing many pre-made functions that allow the TodoJpaResource to perform CRUD on the H2 database

@Repository
public interface TodoJpaRepository extends JpaRepository<Todo, Long> {
    // Custom findByUsername method.
    List<Todo> findByUsername(String username);
}
