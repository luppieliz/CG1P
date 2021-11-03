package com.app.todo.industry;

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

import com.app.todo.business.Business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "industry_id")
    private Long id;

    @NotNull(message = "Industry name should not be null")
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "industry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Business> businesses;

    public Industry(String name) { // all args constructor
        this.name = name;
    }

    public Industry(Long id, String name) { // all args constructor
        this.id = id;
        this.name = name;
    }
}