package com.app.todo.newsfunnel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class News {
    private String publisher;
    private String author;
    private String title;

    @Length(max=5000)
    private String description;
    @Id
    private String URL;
    private String publishedDate;

    @Length(max=50000000)
    private String content;

    public News() {}

    public News(String publisher, String author, String title, String description, String URL, String publishedDate) {
        this.publisher = publisher;
        this.author = author;
        this.title = title;
        this.description = description;
        this.URL = URL;
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "publisher='" + publisher + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", URL='" + URL + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                '}';
    }
}
