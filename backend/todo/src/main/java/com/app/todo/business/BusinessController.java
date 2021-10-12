package com.app.todo.business;

import com.app.todo.user.User;
import com.app.todo.user.UserService;

import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins={ "http://localhost:3000", "http://localhost:4200" })
@RestController
public class BusinessController {
    private BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/business")
    public List<Business> getBusinesses() {
        return businessService.getBusinesses();
    }

    // getmapping for public shld only be able to access by businessname 
    @GetMapping("/business/{businessName}")
    public Business getBusiness(@Valid @PathVariable (value = "businessName") String businessName) {
        return businessService.loadByBusinessName(businessName);
    }

    // should be restricted to either admin or validated business owners
    @PostMapping("/business")
    public Business addBusiness(@Valid @RequestBody Business newBusiness){
        return businessService.addBusiness(newBusiness);
    }

    @DeleteMapping("/business/{businessId}")
    public Business deleteBusiness(@Valid @PathVariable (value = "businessId") Long businessId) {
        return businessService.deleteBusiness(businessId);
    }

    // not rly working sighs
    @PutMapping("/business/{businessId}")
    public Business updateBusiness(@PathVariable Long businessId, @Valid @RequestBody Business newBusiness){
        Business business = businessService.updateBusiness(businessId, newBusiness);
        // if(business == null) throw new BusinessNotFoundException(businessId);
        return business;
    }

    // @GetMapping("/business/{businessId}/users")
    // public List<User> getUsers(@Valid @PathVariable (value = "businessId") Long businessId) {
    //     if(!businessService.existsById(businessId)) {
    //         throw new BusinessNotFoundException(businessId);
    //     }
    //     return reviews.findByBookId(bookId);
    // }
}