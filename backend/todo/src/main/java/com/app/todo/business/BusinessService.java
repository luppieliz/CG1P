package com.app.todo.business;

// import com.app.todo.business.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {
    private BusinessRepository businessRepository;

    @Autowired
    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public Business loadByBusinessName(String businessName) throws BusinessNotFoundException {
        return businessRepository.findByBusinessName(businessName).orElseThrow(
                () -> new BusinessNotFoundException("Business: " + businessName + " is not found!")
        );
    }

    public List<Business> getBusinesses() {
        return businessRepository.findAll();
    }

    // add and delete Business should only be able to be done my admin 
    // if done by admin, should password by generated then passed to business owner?
    // or set by business owner?
    public Business addBusiness(Business newBusiness) {
        if (businessRepository.existsByBusinessName(newBusiness.getBusinessName())) {
            throw new BusinessAlreadyRegisteredException(newBusiness.getBusinessName());
        }
        // newBusiness.setPassword(newBusiness.getPassword());
        return businessRepository.save(newBusiness);
    }

    public Business deleteBusiness(Long businessId) {
        if (!businessRepository.existsById(businessId)) {
            throw new BusinessNotFoundException(businessId);
        }
        Business deleteBusiness = businessRepository.findById(businessId).get();
        businessRepository.deleteById(businessId);
        return deleteBusiness;
    }

    public Business updateBusiness(Long businessId, Business newBusiness) {
        if (!businessRepository.existsById(businessId)) {
            throw new BusinessNotFoundException(businessId);
        }
        Business business = businessRepository.findById(businessId).get();
        business = newBusiness;
        return business;
    }

    // businessRepo methods
    public Boolean existsById(Long businessId) {
        return businessRepository.existsById(businessId);
    }

    public Optional<Business> findById(Long businessId) {
        return businessRepository.findById(businessId);
    }

    public Boolean existsByBusinessName(String businessName) {
        return businessRepository.existsByBusinessName(businessName);
    }
}
