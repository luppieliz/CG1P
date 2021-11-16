package com.app.buddy19.faq;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, UUID> {

    boolean existsByURL(String URL);
}
