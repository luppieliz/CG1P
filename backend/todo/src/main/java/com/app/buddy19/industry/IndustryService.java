package com.app.buddy19.industry;

import java.util.List;
import java.util.UUID;

public interface IndustryService {
    List<Industry> getAllIndustries();

    Industry getIndustry(UUID industryId) throws IndustryNotFoundException;

    Industry getIndustry(String industryName) throws IndustryNotFoundException;

    Industry addIndustry(Industry industry) throws IndustryAlreadyExistsException;
}
