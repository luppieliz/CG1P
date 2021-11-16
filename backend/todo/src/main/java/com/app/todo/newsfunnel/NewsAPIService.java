package com.app.todo.newsfunnel;

import java.io.IOException;

public interface NewsAPIService {
    NewsAPIResponse getAPIResponse(String countryCode, String query) throws IOException, InterruptedException;

    NewsAPIResponse getAPIResponsev2(String sources, String query, String fromDate) throws IOException, InterruptedException;

    NewsDTO[] getNewsDTOArr(NewsAPIResponse newsAPIResponse);
}
