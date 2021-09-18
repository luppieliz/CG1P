package com.app.todo.todo;

import com.app.todo.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
    private Date createdDate;
    private boolean isDone;
}
