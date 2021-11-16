package com.app.buddy19.faq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class FAQ {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID Id;
    private String URL;
    private String industry;
    private String language;

    public FAQ() {
    }

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
