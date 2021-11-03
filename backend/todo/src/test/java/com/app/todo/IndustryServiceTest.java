package com.app.todo;

import com.app.todo.industry.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class IndustryServiceTest {

    @Mock
    private IndustryRepository industries;

    @InjectMocks
    private IndustryService industryService;

    @Test
    void getAllIndustries_ReturnAllIndustries() {
        when(industries.findAll()).thenReturn(new ArrayList<Industry>());

        List<Industry> allIndustries = industryService.getAllIndustries();

        assertNotNull(allIndustries);
        verify(industries).findAll();
    }

    @Test
    void getIndustryWithId_ValidIndustryId_ReturnIndustry() {
        Industry industry = new Industry("Arts and Culture");
        when(industries.findById(industry.getId())).thenReturn(Optional.of(industry)); // findbyid returns business?

        Industry foundIndustry = industryService.getIndustry(industry.getId());

        assertNotNull(foundIndustry);
        verify(industries).findById(industry.getId());
    }

    @Test
    void getIndustryWithId_InvalidIndustryId_ReturnIndustryNotFoundException() {
        Industry industry = new Industry(1L, "Arts and Culture");
        industryService.addIndustry(industry);
        Long testIndustryId = 2L;

        when(industries.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            Industry foundIndustry = industryService.getIndustry(testIndustryId);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IndustryNotFoundException.class, exception.getClass());
        verify(industries).findById(testIndustryId);
    }

    @Test
    void getIndustryWithName_ValidIndustryName_ReturnIndustry() {
        Industry industry = new Industry("Arts and Culture");
        when(industries.findByName(any(String.class))).thenReturn(Optional.of(industry)); // findbyid returns business?

        Industry foundIndustry = industryService.getIndustry(industry.getName());

        assertNotNull(foundIndustry);
        verify(industries).findByName(industry.getName());
    }

    @Test
    void getIndustryWithName_InvalidIndustryName_ReturnIndustryNotFoundException() {
        Industry industry = new Industry("Arts and Culture");
        industryService.addIndustry(industry);
        String testIndustryName = "Arts";

        when(industries.findByName(any(String.class))).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            Industry foundIndustry = industryService.getIndustry(testIndustryName);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IndustryNotFoundException.class, exception.getClass());
        verify(industries).findByName(testIndustryName);
    }

    @Test
    void addIndustry_NewIndustry_ReturnSavedIndustry() {
        Industry industry = new Industry("Arts and Culture");
        when(industries.existsByName(any(String.class))).thenReturn(false);
        when(industries.save(any(Industry.class))).thenReturn(industry);

        Industry savedIndustry = industryService.addIndustry(industry);

        assertNotNull(savedIndustry);
        verify(industries).existsByName(industry.getName());
        verify(industries).save(industry);
    }

    @Test
    void addIndustry_IndustryWithSameName_ReturnIndustryAlreadyExistsException() {
        Industry industry = new Industry("Arts and Culture");
        industryService.addIndustry(industry);
        Industry newIndustry = new Industry("Arts and Culture");

        when(industries.existsByName(any(String.class))).thenReturn(true);

        Throwable exception = null;

        try {
            Industry savedIndustry = industryService.addIndustry(newIndustry);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IndustryAlreadyExistsException.class, exception.getClass());
        verify(industries, atLeast(2)).existsByName(industry.getName());
    }
}
