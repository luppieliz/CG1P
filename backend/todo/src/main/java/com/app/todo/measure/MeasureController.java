package com.app.todo.measure;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path="api/v1/industryMeasures")
public class MeasureController {

    private MeasureService measureService;

    @Autowired
    public MeasureController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @GetMapping
    public List<String> getMeasure() {
        List<String> sourcesLink = measureService.getMeasures();
        sourcesLink.forEach(src -> System.out.println("Link scraped: " + src));
        return sourcesLink;
    }
}
