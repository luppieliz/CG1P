package com.app.todo.todo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.app.todo.user.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Todo {

    @ApiModelProperty(notes = "The database generated todo ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @ApiModelProperty(notes = "Owner of a todo")
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ApiModelProperty(notes = "A todo's description")
    @NotNull(message = "Description should not be null")
    @Size(min = 5, message = "Description should be at least 5 characters long")
    @Column(name = "description")
    private String description;

    @ApiModelProperty(notes = "The date a todo is created")
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @ApiModelProperty(notes = "Target users' IDs for a todo")
    @NotNull(message = "Target users should not be null")
    @Transient
    private List<Long> createdForIds;

    @ApiModelProperty(notes = "Target users for a todo")
    @ManyToMany
    @JoinTable(name = "todo_users",
        joinColumns = @JoinColumn(name = "todo_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "created_for", nullable = false))
    private List<User> createdFor;

    @ApiModelProperty(notes = "State of a todo's completion")
    @Column(name = "is_done")
    private Boolean isDone = false;

    public Todo(String description, List<Long> createdForIds) {
        this.description = description;
        this.createdForIds = createdForIds;
    }
}
