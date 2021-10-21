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

    public Industry getIndustry(Long id) throws IndustryNotFoundException {
        return industryRepository.findById(id).orElseThrow(() -> new IndustryNotFoundException(id));
    }

    public Industry getIndustry(String name) throws IndustryNotFoundException {
        return industryRepository.findByName(name).orElseThrow(() -> new IndustryNotFoundException(name));
    }

    public Industry addIndustry(Industry industry) throws IndustryAlreadyRegistered {
        String name = industry.getName();

        if (industryRepository.existsByName(name)) {
            throw new IndustryAlreadyRegistered(name);
        }

        return industryRepository.save(industry);
    }
}
