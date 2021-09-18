package com.app.todo.measure;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Measure {

    private String businessIndustry;
    private String regulationURL;
    private List<String> scrapedSources;

    @Override
    public String toString() {
        return "Measure [businessIndustry=" + businessIndustry + ", regulationURL=" + regulationURL
                + ", scrapedSources=" + scrapedSources + "]";
    }
}
