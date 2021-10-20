package com.app.todo.business;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.app.todo.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private Long id;

    // TODO: Add proper UEN validation
    @NotNull(message = "UEN should not be null")
    @Column(name = "UEN")
    private String UEN;

    @NotNull(message = "Business name should not be null")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Industry ID should not be null")
    @Column(name = "industry_id")
    private int industryId;

    // @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<User> users;

    public Business(String UEN, String name, @JsonProperty("industry_id") int industryId) {
        this.UEN = UEN;
        this.name = name;
        this.industryId = industryId;
    }
}
