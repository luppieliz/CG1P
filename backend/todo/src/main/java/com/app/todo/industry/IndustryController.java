package com.app.todo.industry;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class IndustryController {
    private IndustryService industryService;

    @Autowired
    public IndustryController(IndustryService industrySerivce) {
        this.industryService = industrySerivce;
    }

    @ApiOperation(value = "Retrieve all the industries stored in database", response = Iterable.class)
    @GetMapping("/industry") 
    public List<Industry> getAllIndustries() {
        return industryService.getAllIndustries();
    }

    @ApiOperation(value = "Retrieve a specific industry given an industry ID", response = Industry.class)
    @GetMapping("/industry/{industryId}")
    public Industry getIndustry(@PathVariable UUID industryId) {
        return industryService.getIndustry(industryId);
    }

    @ApiOperation(value = "Retrieve a specific industry given an industry name", response = Industry.class)
    @GetMapping("/industry/name/{industryName}")
    public Industry getIndustry(@PathVariable String industryName) {
        return industryService.getIndustry(industryName);
    }

    @ApiOperation(value = "Create a new industry and store into database", response = Industry.class)
    @PostMapping("/industry")
    public Industry addIndustry(@Valid @RequestBody Industry industry) {
        return industryService.addIndustry(industry);
    }
}