package com.app.todo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByBusinessId(Long businessId);
    Optional<User> findByIdAndBusinessId(Long Id, Long businessId);
    Optional<User> findByUsername(String username);
}
