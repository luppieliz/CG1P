package com.app.todo;

import com.app.todo.faq.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FAQServiceTest {

    @Mock
    private FAQRepository faqRepository;

    @InjectMocks
    private FAQService faqService;

    @Test
    void addFAQ_NewFAQ_ReturnSavedFAQ() {
        String URL = "www.gobusiness.gov.sg/covid-19-faqs/for-sector-specific-queries/education";
        FAQ newFAQ = new FAQ(URL);

        when(faqRepository.save(any(FAQ.class))).thenReturn(newFAQ);

        FAQ savedFAQ = faqService.addFAQ(newFAQ);

        assertNotNull(savedFAQ);
        assertEquals(newFAQ.getURL(), savedFAQ.getURL());
        verify(faqRepository).save(newFAQ);
    }

    @Test
    void retrieveAllFAQ_ReturnAllSavedFAQ() {
        List<String> scrSrc = new ArrayList<>();
        scrSrc.add("ULR1");
        scrSrc.add("URL2");

        List<FAQ> expectedFAQList = new ArrayList<>();
        FAQ faq1 = new FAQ(scrSrc.get(0));
        FAQ faq2 = new FAQ(scrSrc.get(1));

        expectedFAQList.add(faq1);
        expectedFAQList.add(faq2);

        when(faqRepository.save(any(FAQ.class))).thenReturn(any(FAQ.class));

        List<FAQ> actualFAQList = faqService.retrieveAllFAQ(scrSrc);

        assertNotNull(actualFAQList);
        assertEquals(actualFAQList.size(), actualFAQList.size());
        verify(faqRepository, atLeast(2)).save(any(FAQ.class));

    }

    @Test
    void retrieveAllFAQ_ExistedFAQ_ReturnFAQAlreadyExistedException() {
        List<String> scrSrc = new ArrayList<>();
        String commonURL = "URL1";
        scrSrc.add(commonURL);

        lenient().when(faqRepository.existsByURL(commonURL)).thenReturn(true);

        Throwable exception = null;
        List<FAQ> resultList = null;

        try {
            resultList = faqService.retrieveAllFAQ(scrSrc);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(FAQAlreadyExistedException.class, exception.getClass());
        assertNull(resultList);
    }
}
