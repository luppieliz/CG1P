package com.app.buddy19.industry;

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
public class IndustryController {
    private IndustryService industryService;

    @Autowired
    public IndustryController(IndustryService industrySerivce) {
        this.industryService = industrySerivce;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved an industry list/ an industry"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})

    @ApiOperation(value = "Retrieve all the industries stored in database", response = List.class)
    @GetMapping(path = "/industry", produces = "application/json")
    public List<Industry> getAllIndustries() {
        return industryService.getAllIndustries();
    }

    @ApiOperation(value = "Retrieve a specific industry given an industry ID", response = Industry.class)
    @GetMapping(path = "/industry/{industryId}", produces = "application/json")
    public Industry getIndustry(@PathVariable UUID industryId) {
        return industryService.getIndustry(industryId);
    }

    @ApiOperation(value = "Retrieve a specific industry given an industry name", response = Industry.class)
    @GetMapping(path = "/industry/name/{industryName}", produces = "application/json")
    public Industry getIndustry(@PathVariable String industryName) {
        return industryService.getIndustry(industryName);
    }

    @ApiOperation(value = "Create a new industry and store into database", response = Industry.class)
    @PostMapping(path = "/industry", produces = "application/json")
    public Industry addIndustry(@Valid @RequestBody Industry industry) {
        return industryService.addIndustry(industry);
    }
}