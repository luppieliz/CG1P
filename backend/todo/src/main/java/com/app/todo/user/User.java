package com.app.todo.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.app.todo.business.Business;
import com.app.todo.todo.Todo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

/*
 * Implementations of UserDetails to provide user information to Spring
 * Security, e.g., what authorities (roles) are granted to the user and whether
 * the account is enabled or not
 */
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonDeserialize(using= UUIDDeserializer.class)
    @Column(name = "user_id")
    private UUID id;

    @NotNull(message = "Email should not be null")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Name should not be null")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Password should not be null")
    @Size(min = 8, message = "Password should be at least 8 characters")
    @Column(name = "password")
    private String password;

    @NotNull(message = "SHN status should not be null")
    @Column(name = "shn_status")
    private Boolean shnStatus;

    @NotNull(message = "Covid status should not be null")
    @Column(name = "covid_status")
    private Boolean covidStatus;

    @NotNull(message = "Authority should not be null")
    // We define three roles/authorities: ROLE_ADMIN, ROLE_BUSINESSOWNER, ROLE_EMPLOYEE
    @Column(name = "authority")
    private String authority;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> createdTodos;

    @JsonIgnore
    @ManyToMany(mappedBy = "createdFor", cascade = CascadeType.ALL)
    private List<Todo> assignedTodos;

    public User(String email, String name, String password, String authority, Business business) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.shnStatus = false;
        this.covidStatus = false;
        this.authority = authority;
        this.business = business;
    }

    public User(String email, String name, String password, Boolean shnStatus, Boolean covidStatus, String authority,
            Business business) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.shnStatus = shnStatus;
        this.covidStatus = covidStatus;
        this.authority = authority;
        this.business = business;
    }

    public User(UUID id, String email, String name, String password, String authority, Business business) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.shnStatus = false;
        this.covidStatus = false;
        this.authority = authority;
        this.business = business;
    }

    /*
     * Return a collection of authorities granted to the user.
     */
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(authority));
    }

    /*
     * The various is___Expired() methods return a boolean to indicate whether or
     * not the user’s account is enabled or expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }
}