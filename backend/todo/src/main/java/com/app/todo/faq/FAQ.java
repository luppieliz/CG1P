package com.app.todo.faq;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class FAQ {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String URL;
    private String industry;
    private String language;

    public FAQ() {}
}
