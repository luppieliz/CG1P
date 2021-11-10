package com.app.todo.industry;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.app.todo.business.Business;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "industry_id")
    private UUID id;

    @NotNull(message = "Industry name should not be null")
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "industry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Business> businesses;

    public Industry(String name) { // all args constructor
        this.name = name;
    }

    public Industry(UUID id, String name) { // all args constructor
        this.id = id;
        this.name = name;
    }

    public Industry(String name, List<Business> businesses) {
        this.name = name;
        this.businesses = businesses;
    }
}