package com.app.buddy19.todo;

import com.app.buddy19.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Todo {

    @ApiModelProperty(notes = "The database generated todo ID")
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "todo_id")
    private UUID id;

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

    @ApiModelProperty(notes = "The target date for a todo")
    @Column(name = "target_date")
    private Date targetDate;

    @ApiModelProperty(notes = "Target users' IDs for a todo")
    @NotNull(message = "Target users should not be null")
    @Transient
    private List<UUID> createdForIds;

    @ApiModelProperty(notes = "Target users for a todo")
    @ManyToMany
    @JoinTable(name = "todo_users",
            joinColumns = @JoinColumn(name = "todo_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "created_for", nullable = false))
    private List<User> createdFor;

    public Todo(String description, Date targetDate, List<UUID> createdForIds) {
        this.description = description;
        this.targetDate = targetDate;
        this.createdForIds = createdForIds;
    }

    public Todo(UUID id, String description, Date targetDate, List<UUID> createdForIds) {
        this.id = id;
        this.description = description;
        this.targetDate = targetDate;
        this.createdForIds = createdForIds;
    }

    public Todo(User createdBy, Date createdDate, String description) {
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.description = description;
    }

    public Todo(User createdBy, Date createdDate, String description, List<UUID> createdForIDs) {
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.description = description;
        this.createdForIds = createdForIDs;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", createdBy=" + createdBy +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", targetDate=" + targetDate +
                ", createdForIds=" + createdForIds +
                ", createdFor=" + createdFor +
                '}';
    }
}