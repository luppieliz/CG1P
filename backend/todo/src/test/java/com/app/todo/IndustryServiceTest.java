package com.app.todo;

import com.app.todo.industry.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private IndustryRepository industryRepository;

    @InjectMocks
    private IndustryService industryService;

    @Test
    void getAllIndustries_ReturnAllIndustries() {
        Industry industry = industryRepository.save(new Industry("Arts and Culture"));
        List<Industry> industryList = new ArrayList<>();
        industryList.add(industry);

        when(industryRepository.findAll()).thenReturn(industryList);

        List<Industry> allIndustries = industryService.getAllIndustries();

        assertNotNull(allIndustries);
        assertEquals(industryList.size(), allIndustries.size());
        verify(industryRepository).findAll();
    }

    @Test
    void getIndustryWithId_ValidIndustryId_ReturnIndustry() {
        UUID testUUID = UUID.randomUUID();
        Industry industry = new Industry(testUUID, "Arts and Culture");
        when(industryRepository.findById(any(UUID.class))).thenReturn(Optional.of(industry)); // findbyid returns business?

        Industry foundIndustry = industryService.getIndustry(industry.getId());

        assertNotNull(foundIndustry);
        assertEquals(industry.getId(), foundIndustry.getId());
        verify(industryRepository).findById(industry.getId());
    }

    @Test
    void getIndustryWithId_InvalidIndustryId_ReturnIndustryNotFoundException() {
        Industry industry = new Industry(UUID.randomUUID(), "Arts and Culture");
        industryService.addIndustry(industry);
        UUID testIndustryId = UUID.randomUUID();

        when(industryRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            Industry foundIndustry = industryService.getIndustry(testIndustryId);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IndustryNotFoundException.class, exception.getClass());
        verify(industryRepository).findById(testIndustryId);
    }

    @Test
    void getIndustryWithName_ValidIndustryName_ReturnIndustry() {
        Industry industry = new Industry("Arts and Culture");
        when(industryRepository.findByName(any(String.class))).thenReturn(Optional.of(industry)); // findbyid returns business?

        Industry foundIndustry = industryService.getIndustry(industry.getName());

        assertNotNull(foundIndustry);
        assertEquals(industry.getName(), foundIndustry.getName());
        verify(industryRepository).findByName(industry.getName());
    }

    @Test
    void getIndustryWithName_InvalidIndustryName_ReturnIndustryNotFoundException() {
        Industry industry = new Industry("Arts and Culture");
        industryService.addIndustry(industry);
        String testIndustryName = "Arts";

        when(industryRepository.findByName(any(String.class))).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            Industry foundIndustry = industryService.getIndustry(testIndustryName);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IndustryNotFoundException.class, exception.getClass());
        verify(industryRepository).findByName(testIndustryName);
    }

    @Test
    void addIndustry_NewIndustry_ReturnSavedIndustry() {
        Industry industry = new Industry("Arts and Culture");
        when(industryRepository.existsByName(any(String.class))).thenReturn(false);
        when(industryRepository.save(any(Industry.class))).thenReturn(industry);

        Industry savedIndustry = industryService.addIndustry(industry);

        assertNotNull(savedIndustry);
        verify(industryRepository).existsByName(industry.getName());
        verify(industryRepository).save(industry);
    }

    @Test
    void addIndustry_IndustryWithSameName_ReturnIndustryAlreadyExistsException() {
        Industry industry = new Industry("Arts and Culture");
        industryService.addIndustry(industry);
        Industry newIndustry = new Industry("Arts and Culture");

        when(industryRepository.existsByName(any(String.class))).thenReturn(true);

        Throwable exception = null;

        try {
            Industry savedIndustry = industryService.addIndustry(newIndustry);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(IndustryAlreadyExistsException.class, exception.getClass());
        verify(industryRepository, atLeast(2)).existsByName(industry.getName());
    }
}
