package com.app.todo.faq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FAQService {

    private FAQRepository faqRepository;
    private static Map<String, List<String>> languageMap = new HashMap<>();
    private static Map<String, List<String>> businessMap = new HashMap<>();

    @Autowired
    public FAQService(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    static {
        String[] english = new String[] {"english"};
        languageMap.put("English", Arrays.asList(english));

        String[] chinese = new String[] {"chinese"};
        languageMap.put("Chinese", Arrays.asList(chinese));

        String[] fnb = new String[] {"fb"};
        businessMap.put("F&B", Arrays.asList(fnb));

        String[] retail = new String[] {"retail"};
        businessMap.put("Retail", Arrays.asList(retail));

        String[] delivery = new String[] {"delivery"};
        businessMap.put("Delivery", Arrays.asList(delivery));
    }

    /**
     * Add a new FAQ to database
     * @param faqURL
     * @return a newly added FAQ with a known language and a known industry.
     */
    public FAQ addFAQ(final String faqURL) {
        FAQ newFAQ = new FAQ();
        newFAQ.setURL(faqURL);
        newFAQ.setLanguage(findLanguage(faqURL));
        newFAQ.setIndustry(findIndustry(faqURL));
        return faqRepository.save(newFAQ);
    }

    /**
     * Find the language of a given FAQ
     * @param faqURL
     * @return A string language if key word can be found.
     */
    public String findLanguage(final String faqURL) {
        for (String key : languageMap.keySet()) {
            List<String> values = languageMap.get(key);
            for (String value : values) {
                if (faqURL.contains(value)) {
                    return key;
                }
            }
        }
        return null;
    }

    /**
     * Find the industry of a given FAQ
     * @param faqURL
     * @return A string industry if key word can be found.
     */
    public String findIndustry(final String faqURL) {
        for (String key : businessMap.keySet()) {
            List<String> values = businessMap.get(key);
            for (String value : values) {
                if (faqURL.contains(value)) {
                    return key;
                }
            }
        }
        return null;
    }
}
