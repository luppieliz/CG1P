package com.app.todo.industry;

import com.app.todo.business.Business;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

    @OneToMany(mappedBy = "industry")
    @Column(name = "business")
    private List<Business> businesses;

    public Industry(String name) { // all args constructor
        this.name = name;
    }
}
