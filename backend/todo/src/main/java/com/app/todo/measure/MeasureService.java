package com.app.todo.measure;

import java.util.List;
import java.util.Map;

public interface MeasureService {
    List<String> getMeasures(String measureURL);

    List<String> getTag(String newsURL);

    Map<String, List<String>> getTagMap(List<String> newsURLList);
}
