package com.app.todo.measure;

import java.util.List;

//import com.app.todo.newsfunnel.Tag;
import com.app.todo.scrapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureService {

    private ScrapperService scrapperService;

    @Autowired
    public MeasureService(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    public List<String> getMeasures(String URL) {
        List<String> sourcesList = scrapperService.scrapeFAQ(URL);
        return sourcesList;
    }

    public void getTag(String newsURL, List<String> tag) {
        scrapperService.scrapeArticle(newsURL, tag);
    }
}
