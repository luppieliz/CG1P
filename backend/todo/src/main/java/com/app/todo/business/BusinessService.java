package com.app.todo.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BusinessService {
    private BusinessRepository businessRepository;

    @Autowired
    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    /**
     * Get all the registered businesses.
     * @return A list of all the registered businesses.
     */
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    /**
     * Get a specific business with a given businessID.
     * @param businessId
     * @return A specific business with a given businessID. If it cannot be found, throws BusinessNotFoundException.
     * @throws BusinessNotFoundException
     */
    public Business getBusiness(UUID businessId) throws BusinessNotFoundException {
        return businessRepository.findById(businessId).orElseThrow(() -> new BusinessNotFoundException(businessId));
    }

    /**
     * Get a specific business with a given UEN.
     * @param UEN
     * @return A specific business with a given UEN. If it cannot be found, throws BusinessNotFoundException.
     * @throws BusinessNotFoundException
     */
    public Business getBusiness(String UEN) throws BusinessNotFoundException {
        return businessRepository.findByUEN(UEN).orElseThrow(() -> new BusinessNotFoundException(UEN));
    }

    /**
     * Add a new business
     * @param business
     * @return A newly registered business. If it is already existed, throws BusinessAlreadyRegisteredException.
     * @throws BusinessAlreadyRegisteredException
     */
    public Business addBusiness(Business business) throws BusinessAlreadyRegisteredException {
        String UEN = business.getUEN();

        if (businessRepository.existsByUEN(UEN)) {
            throw new BusinessAlreadyRegisteredException(UEN);
        }


        return businessRepository.save(business);
    }

    public List<Business> getAllBusinessByIndustryName(String industryName) {
        return businessRepository.findByIndustry_Name(industryName);
    }

    // TODO: Implement other business services

    // public Business deleteBusiness(Long businessId) {
    // if (!businessRepository.existsById(businessId)) {
    // throw new BusinessNotFoundException(businessId);
    // }
    // Business deleteBusiness = businessRepository.findById(businessId).get();
    // businessRepository.deleteById(businessId);
    // return deleteBusiness;
    // }

    // public Business updateBusiness(Long businessId, Business newBusiness) {
    // if (!businessRepository.existsById(businessId)) {
    // throw new BusinessNotFoundException(businessId);
    // }
    // Business business = businessRepository.findById(businessId).get();
    // business = newBusiness;
    // return business;
    // }
}