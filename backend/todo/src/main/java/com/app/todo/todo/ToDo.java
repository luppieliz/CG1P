package com.app.todo.todo;

import com.app.todo.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table
public class ToDo {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @NotNull(message = "Description should not be null")
    @Size(min = 5, message = "Description should be at least 5 characters long")
    private String description;

    private Date createdDate = new Date();
    private Boolean isDone = false;

    public ToDo() {

    }

    public ToDo(@JsonProperty("description") String description) {
        this.description = description;
    }

    public ToDo(Long id, User user, String description, Date createdDate, Boolean isDone) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.createdDate = createdDate;
        this.isDone = isDone;
    }

}
