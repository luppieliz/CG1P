package com.app.buddy19.newsfunnel;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.Map;


@Getter
@Setter
public class NewsDTO {
    private Map<Object, String> source;
    private String author;
    private String title;
    private String description;

    @Id
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    public NewsDTO() {
    }

    public NewsDTO(Map<Object, String> source, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsDTO{" +
                "source=" + source +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
