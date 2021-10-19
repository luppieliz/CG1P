package com.app.todo.measure;

import java.util.List;

//import com.app.todo.newsfunnel.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path="api/v1/scrape")
public class MeasureController {

    private MeasureService measureService;

    @Autowired
    public MeasureController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @ApiOperation(value = "Scrape for measures")
    @GetMapping(path="/industryMeasures",produces = "application/json")
    public List<String> getMeasure(String URL) {
        List<String> sourcesLink = measureService.getMeasures(URL);
        sourcesLink.forEach(src -> System.out.println("Link scraped: " + src));
        return sourcesLink;
    }

    @ApiOperation(value = "Scrape for article tag")
    @GetMapping(path="/newsTag",produces = "application/json")
    public void getTag(String newsURL, List<String> tag) {
        measureService.getTag(newsURL, tag);
    }
}
