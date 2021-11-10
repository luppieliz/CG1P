package com.app.todo;

import com.app.todo.business.*;
import com.app.todo.industry.Industry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.todo.industry.IndustryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BusinessServiceTest {

    @Mock
    private BusinessRepository businessRepository;

    @InjectMocks
    private BusinessService businessService;

    @Mock
    private IndustryRepository industryRepository;

    @Test
    void getAllBusinesses_ReturnAllBusinesses() {
        Industry industry = new Industry("Arts and Culture");
        Business testBusiness1 = businessService.addBusiness(new Business("asd789fhgj", "Singapore Museum", industry));
        Business testBusiness2 = businessService.addBusiness(new Business("asd799fhgj", "SMU Museum", industry));

        List<Business> businessList = new ArrayList<>();
        businessList.add(testBusiness1);
        businessList.add(testBusiness2);

        when(businessRepository.findAll()).thenReturn(businessList);
        List<Business> allBusinesses = businessService.getAllBusinesses();

        assertNotNull(allBusinesses);
        assertEquals(2, allBusinesses.size());
        verify(businessRepository).findAll();
    }

    @Test
    void getBusinessWithId_ValidBusinessId_ReturnBusiness() {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));

        UUID testUUID = UUID.randomUUID();
        Business business = new Business(testUUID, "asd789fhgj", "Singapore Museum", industry);

        when(businessRepository.findById(any(UUID.class))).thenReturn(Optional.of(business));

        Business theBusiness = businessService.getBusiness(testUUID);

        assertNotNull(theBusiness);
        verify(businessRepository).findById(testUUID);
    }

    @Test
    void getBusinessWithId_InvalidBusinessId_ReturnBusinessNotFoundException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = businessRepository
                .save(new Business(UUID.randomUUID(), "asd789fhgj", "Singapore Museum", industry));
        UUID testBusinessId = UUID.randomUUID();

        when(businessRepository.findById(testBusinessId)).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            Business foundBusiness = businessService.getBusiness(testBusinessId);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(BusinessNotFoundException.class, exception.getClass());
        verify(businessRepository).findById(testBusinessId);
    }

    @Test
    void getBusinessWithUEN_ValidBusinessUEN_ReturnBusiness() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        when(businessRepository.findByUEN(business.getUEN())).thenReturn(Optional.of(business)); // findbyid returns
                                                                                                 // business?

        Business theBusiness = businessService.getBusiness(business.getUEN());

        assertNotNull(theBusiness);
        assertEquals(business.getUEN(), theBusiness.getUEN());
        verify(businessRepository).findByUEN(business.getUEN());
    }

    @Test
    void getBusinessWithUEN_InvalidBusinessUEN_ReturnBusinessNotFoundException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        businessService.addBusiness(business);
        String testBusinessUEN = "asd788fhgj";

        when(businessRepository.findByUEN(testBusinessUEN)).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            Business foundBusiness = businessService.getBusiness(testBusinessUEN);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(BusinessNotFoundException.class, exception.getClass());
        verify(businessRepository).findByUEN(testBusinessUEN);
    }

    @Test
    void addBusiness_NewBusiness_ReturnSavedBusiness() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd829fhgj", "Singapore Museum", industry);
        when(businessRepository.existsByUEN(any(String.class))).thenReturn(false);
        when(businessRepository.save(any(Business.class))).thenReturn(business);

        Business savedBusiness = businessService.addBusiness(business);

        assertNotNull(savedBusiness);
        verify(businessRepository).existsByUEN(business.getUEN());
        verify(businessRepository).save(business);
    }

    @Test
    void addBusiness_BusinesWithSameUEN_ReturnBusinessAlreadyRegisteredException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        businessService.addBusiness(business);
        Business newBusiness = new Business("asd789fhgj", "Singapore Museum", industry);

        when(businessRepository.existsByUEN(business.getUEN())).thenReturn(true);

        Throwable exception = null;

        try {
            Business savedBusiness = businessService.addBusiness(newBusiness);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(BusinessAlreadyRegisteredException.class, exception.getClass());
        verify(businessRepository, atLeast(2)).existsByUEN(business.getUEN());

    }
}
