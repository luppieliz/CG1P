package com.app.todo.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {
    private BusinessRepository businessRepository;

    @Autowired
    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    public Business getBusiness(Long businessId) throws BusinessNotFoundException {
        return businessRepository.findById(businessId).orElseThrow(() -> new BusinessNotFoundException(businessId));
    }

    public Business getBusiness(String UEN) throws BusinessNotFoundException {
        return businessRepository.findByUEN(UEN).orElseThrow(() -> new BusinessNotFoundException(UEN));
    }

    public Business addBusiness(Business business) throws BusinessAlreadyRegisteredException {
        String UEN = business.getUEN();

        if (businessRepository.existsByUEN(UEN)) {
            throw new BusinessAlreadyRegisteredException(UEN);
        }

        return businessRepository.save(business);
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