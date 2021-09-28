package com.app.todo.user;

import com.app.todo.todo.ToDo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @NotNull(message = "Username should not be null")
    @Size(min = 5, max = 20, message = "Username should be between 5 and 20 characters")
    @Column(name = "username")
    private String username;

    @NotNull(message = "Email should not be null")
    @Email
    @Column(name = "email")
    private String email;

    @NotNull(message = "Password should not be null")
    @Size(min = 8, message = "Password should be at least 8 characters")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Authority should not be null")
    // We define two roles/authorities: ROLE_USER or ROLE_ADMIN
    @Column(name = "authority")
    private String authority;

    @OneToMany(mappedBy= "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Column(name = "todo")
    private List<ToDo> toDos;

    public User(@JsonProperty("username") String username,
                @JsonProperty("email") String email,
                @JsonProperty("password") String password,
                @JsonProperty("authority") String authority) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    /* Return a collection of authorities (roles) granted to the user.
     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//        authorities.forEach(authority -> authorityList.add(new SimpleGrantedAuthority(authority)));
//        return authorityList;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(authority));
    }

    /*
    The various is___Expired() methods return a boolean to indicate whether
    or not the user’s account is enabled or expired.
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<ToDo> getToDos() {
        return toDos;
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }
}