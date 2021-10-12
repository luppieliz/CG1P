package com.app.todo.business;

import com.app.todo.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table
public class Business {
    
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    Long id;

    @NotNull(message = "Business name should not be null")
    @Size(min = 5, max = 20, message = "Business name should be between 5 and 20 characters")
    @Column(name = "business_name")
    private String businessName;

    @NotNull(message = "Industry ID should not be null")
    @Column(name = "industry_id")
    private int industryId;

    @OneToMany(mappedBy= "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    public Business(@JsonProperty("business_id") Long id,
                @JsonProperty("business_name") String businessName,
                @JsonProperty("industry_id") int industryId) {
        this.id = id;
        this.businessName = businessName;
        this.industryId = industryId;
    }
}
