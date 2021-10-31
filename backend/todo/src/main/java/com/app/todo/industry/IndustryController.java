package com.app.todo.industry;

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
public class IndustryController {
    private IndustryService industryService;

    @Autowired
    public IndustryController(IndustryService industrySerivce) {
        this.industryService = industrySerivce;
    }

    @GetMapping("/industry") 
    public List<Industry> getAllIndustries() {
        return industryService.getAllIndustries();
    }

    @GetMapping("/industry/{industryId}")
    public Industry getIndustry(@PathVariable Long industryId) {
        return industryService.getIndustry(industryId);
    }

    @GetMapping("/industry/name/{industryName}")
    public Industry getIndustry(@PathVariable String industryName) {
        return industryService.getIndustry(industryName);
    }

    @PostMapping("/industry")
    public Industry addIndustry(@Valid @RequestBody Industry industry) {
        return industryService.addIndustry(industry);
    }
}