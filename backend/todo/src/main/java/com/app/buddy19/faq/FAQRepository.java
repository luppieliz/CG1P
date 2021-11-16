package com.app.buddy19.faq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, UUID> {

    boolean existsByURL(String URL);
}
