package com.app.todo;

import com.app.todo.business.*;
import com.app.todo.industry.Industry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BusinessServiceTest {
    
    @Mock
    private BusinessRepository businesses;

    @InjectMocks
    private BusinessService businessService;

    @Test
    void getAllBusinesses_ReturnAllBusinesses() {
        when(businesses.findAll()).thenReturn(new ArrayList<Business>());

        List<Business> allBusinesses = businessService.getAllBusinesses();

        assertNotNull(allBusinesses);
        verify(businesses).findAll();
    }

    @Test
    void getBusinessWithId_ValidBusinessId_ReturnBusiness() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        when(businesses.findById(business.getId())).thenReturn(Optional.of(business)); // findbyid returns business?

        Business theBusiness = businessService.getBusiness(business.getId());

        assertNotNull(theBusiness);
        verify(businesses).findById(business.getId());
    }

    @Test
    void getBusinessWithId_InvalidBusinessId_ReturnBusinessNotFoundException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business(1L, "asd789fhgj", "Singapore Museum", industry);
        businessService.addBusiness(business);
        Long testBusinessId = 2L;

        when(businesses.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            Business foundBusiness = businessService.getBusiness(testBusinessId);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(BusinessNotFoundException.class, exception.getClass());
        verify(businesses).findById(testBusinessId);
    }

    @Test
    void getBusinessWithUEN_ValidBusinessUEN_ReturnBusiness() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        when(businesses.findByUEN(business.getUEN())).thenReturn(Optional.of(business)); // findbyid returns business?

        Business theBusiness = businessService.getBusiness(business.getUEN());

        assertNotNull(theBusiness);
        verify(businesses).findByUEN(business.getUEN());
    }

    @Test
    void getBusinessWithUEN_InvalidBusinessUEN_ReturnBusinessNotFoundException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        businessService.addBusiness(business);
        String testBusinessUEN = "asd788fhgj";

        when(businesses.findByUEN(any(String.class))).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            Business foundBusiness = businessService.getBusiness(testBusinessUEN);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(BusinessNotFoundException.class, exception.getClass());
        verify(businesses).findByUEN(testBusinessUEN);
    }

    @Test
    void addBusiness_NewBusiness_ReturnSavedBusiness() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd829fhgj", "Singapore Museum", industry);
        when(businesses.existsByUEN(any(String.class))).thenReturn(false);
        when(businesses.save(any(Business.class))).thenReturn(business);

        Business savedBusiness = businessService.addBusiness(business);

        assertNotNull(savedBusiness);
        verify(businesses).existsByUEN(business.getUEN());
        verify(businesses).save(business);
    }

    @Test
    void addBusiness_BusinesWithSameUEN_ReturnBusinessAlreadyRegisteredException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        businessService.addBusiness(business);
        Business newBusiness = new Business("asd789fhgj", "Singapore Museum", industry);

        when(businesses.existsByUEN(any(String.class))).thenReturn(true);

        Throwable exception = null;

        try {
            Business savedBusiness = businessService.addBusiness(newBusiness);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(BusinessAlreadyRegisteredException.class, exception.getClass());
        verify(businesses, atLeast(2)).existsByUEN(business.getUEN());


    }
}
