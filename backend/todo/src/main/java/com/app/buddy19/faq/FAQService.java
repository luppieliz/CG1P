package com.app.buddy19.faq;

import java.util.List;

public interface FAQService {
    List<FAQ> retrieveAllFAQ(List<String> scrappedSrc);

    List<FAQ> updateFAQ(List<String> scrappedSrc);

    FAQ addFAQ(FAQ newFAQ);

    String findLanguage(String faqURL);

    String findIndustry(String faqURL);

    List<FAQ> getAllFAQ();
}
