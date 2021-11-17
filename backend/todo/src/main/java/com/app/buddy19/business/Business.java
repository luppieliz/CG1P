package com.app.buddy19.business;

import com.app.buddy19.industry.Industry;
import com.app.buddy19.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "business_id")
    private UUID id;

    @NotNull(message = "UEN should not be null")
    @Column(name = "UEN")
    private String UEN;

    @NotNull(message = "Business name should not be null")
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    @JsonIgnore
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    public Business(String UEN, String name, Industry industry) { // @JsonProperty("industry_id") int industryId) {
        this.UEN = UEN;
        this.name = name;
        this.industry = industry;
    }

    public Business(@JsonProperty("UEN") String UEN) {
        this.UEN = UEN;
    }

    public Business(UUID id, String UEN, String name, Industry industry) {
        this.id = id;
        this.UEN = UEN;
        this.name = name;
        this.industry = industry;
    }
}