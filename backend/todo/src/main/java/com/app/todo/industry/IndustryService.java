package com.app.todo.industry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndustryService {
    private IndustryRepository industryRepository;

    @Autowired
    public IndustryService(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    public List<Industry> getAllIndustries() {
        return industryRepository.findAll();
    }

    public Industry getIndustry(Long industryId) throws IndustryNotFoundException {
        return industryRepository.findById(industryId).orElseThrow(() -> new IndustryNotFoundException(industryId));
    }

    public Industry getIndustry(String industryName) throws IndustryNotFoundException {
        return industryRepository.findByName(industryName).orElseThrow(() -> new IndustryNotFoundException(industryName));
    }

    public Industry addIndustry(Industry industry) throws IndustryAlreadyExistsException {
        String industryName = industry.getName();

        if (industryRepository.existsByName(industryName)) {
            throw new IndustryAlreadyExistsException(industryName);
        }

        return industryRepository.save(industry);
    }
}