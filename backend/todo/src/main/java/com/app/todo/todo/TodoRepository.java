package com.app.todo.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCreatedBy_Id(Long id);
    List<Todo> findByCreatedFor_Id(Long id);
}
