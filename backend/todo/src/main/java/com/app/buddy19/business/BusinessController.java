package com.app.buddy19.business;

import com.app.buddy19.industry.Industry;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BusinessController {
    private BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved a business list/ a business entity"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})

    @ApiOperation(value = "Retrieve all businesses", response = List.class)
    @GetMapping(path = "/business", produces = "application/json")
    public List<Business> getAllBusinesses() {
        return businessService.getAllBusinesses();
    }

    @ApiOperation(value = "Get a specific business given a business id", response = Business.class)
    @GetMapping(path = "/business/{businessId}", produces = "application/json")
    public Business getBusiness(@PathVariable UUID businessId) {
        return businessService.getBusiness(businessId);
    }

    @ApiOperation(value = "Get a specific business given a business UEN", response = Business.class)
    @GetMapping(path = "/business/uen/{UEN}", produces = "application/json")
    public Business getBusiness(@PathVariable String UEN) {
        return businessService.getBusiness(UEN);
    }

    @ApiOperation(value = "Add a new business", response = Business.class)
    @PostMapping(path = "/business", produces = "application/json")
    public Business addBusiness(@Valid @RequestBody Business business) {
        return businessService.addBusiness(business);
    }
}