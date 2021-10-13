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

    public FAQ addFAQ(String URL) {
        FAQ newFAQ = new FAQ();
        newFAQ.setURL(URL);

        for (String key : languageMap.keySet()) {
            List<String> values = languageMap.get(key);
            for (String value : values) {
                if (URL.contains(value)) {
                    newFAQ.setLanguage(key);
                    break;
                }
            }
        }

        for (String key : businessMap.keySet()) {
            List<String> values = businessMap.get(key);
            for (String value : values) {
                if (URL.contains(value)) {
                    newFAQ.setIndustry(key);
                    break;
                }
            }
        }

        return faqRepository.save(newFAQ);
    }
}
