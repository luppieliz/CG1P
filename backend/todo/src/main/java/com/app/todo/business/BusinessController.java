package com.app.todo.business;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BusinessController {
    private BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/business")
    public List<Business> getAllBusinesses() {
        return businessService.getAllBusinesses();
    }

    @GetMapping("/business/{businessId}")
    public Business getBusiness(@PathVariable Long businessId) {
        return businessService.getBusiness(businessId);
    }

    @GetMapping("/business/uen/{UEN}")
    public Business getBusiness(@PathVariable String UEN) {
        return businessService.getBusiness(UEN);
    }

    @PostMapping("/business")
    public Business addBusiness(@Valid @RequestBody Business business) {
        return businessService.addBusiness(business);
    }

    // @DeleteMapping("/business/{businessId}")
    // public Business deleteBusiness(@Valid @PathVariable(value = "businessId") Long businessId) {
    //     return businessService.deleteBusiness(businessId);
    // }

    // // not rly working sighs
    // @PutMapping("/business/{businessId}")
    // public Business updateBusiness(@PathVariable Long businessId, @Valid @RequestBody Business newBusiness) {
    //     Business business = businessService.updateBusiness(businessId, newBusiness);
    //     // if(business == null) throw new BusinessNotFoundException(businessId);
    //     return business;
    // }

    // @GetMapping("/business/{businessId}/users")
    // public List<User> getUsers(@Valid @PathVariable (value = "businessId") Long
    // businessId) {
    // if(!businessService.existsById(businessId)) {
    // throw new BusinessNotFoundException(businessId);
    // }
    // return reviews.findByBookId(bookId);
    // }
}