package com.app.todo.faq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class FAQ {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String URL;
    private String industry;
    private String language;

    public FAQ() {}

    public FAQ(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "FAQ{" +
                "Id=" + Id +
                ", URL='" + URL + '\'' +
                ", industry='" + industry + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
