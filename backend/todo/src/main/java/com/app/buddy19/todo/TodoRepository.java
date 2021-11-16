package com.app.buddy19.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {
    List<Todo> findByCreatedBy_Id(UUID id);

    List<Todo> findByCreatedFor_Id(UUID id);
}
