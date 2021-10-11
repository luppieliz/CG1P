package com.app.todo.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.app.todo.todo.ToDo;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

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

    @NotNull(message = "Authority should not be null")
    // We define three roles/authorities: ROLE_ADMIN, ROLE_BUSINESSOWNER, ROLE_EMPLOYEE
    @Column(name = "authority")
    private String authority;

    @OneToMany(mappedBy= "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "todo")
    private List<ToDo> toDos;

    public User(String email, String name, String password, String authority) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.authority = authority;
    }

    /*
     * Return a collection of authorities granted to the user.
     */
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