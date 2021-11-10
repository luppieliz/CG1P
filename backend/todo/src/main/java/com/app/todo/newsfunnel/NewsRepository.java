package com.app.todo.newsfunnel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {

    boolean existsByTitle(String title);
    boolean existsByURL(String URL);
}
