package com.app.buddy19.industry;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndustryServiceImpl implements IndustryService {
    private IndustryRepository industryRepository;

    @Autowired
    public IndustryServiceImpl(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    /**
     * Get all stored industries.
     * @return A list of stored industries.
     */
    @Override
    public List<Industry> getAllIndustries() {
        return industryRepository.findAll();
    }

    /**
     * Get a specific industry with a given industryID.
     * @param industryId
     * @return An industry with a given industryID. If it is already existed, throw an IndustryAlreadyExistsException.
     * @throws IndustryNotFoundException
     */
    @Override
    public Industry getIndustry(UUID industryId) throws IndustryNotFoundException {
        return industryRepository.findById(industryId).orElseThrow(() -> new IndustryNotFoundException(industryId));
    }

    /**
     * Get a specific industry with a given industryName.
     * @param industryName
     * @return An industry with a given industryName. If it is already existed, throw an IndustryAlreadyExistsException.
     * @throws IndustryNotFoundException
     */
    @Override
    public Industry getIndustry(String industryName) throws IndustryNotFoundException {
        return industryRepository.findByName(industryName).orElseThrow(() -> new IndustryNotFoundException(industryName));
    }

    /**
     * Add a new industry.
     * @param industry
     * @return A newly added industry. If it is already existed, throw an IndustryAlreadyExistsException.
     * @throws IndustryAlreadyExistsException
     */
    @Override
    public Industry addIndustry(Industry industry) throws IndustryAlreadyExistsException {
        String industryName = industry.getName();

        if (industryRepository.existsByName(industryName)) {
            throw new IndustryAlreadyExistsException(industryName);
        }

        return industryRepository.save(industry);
    }
}