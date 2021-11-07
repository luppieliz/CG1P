package com.app.todo.newsfunnel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
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

    private String tagList;

    public News() {}

}
