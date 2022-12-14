package ru.university.portal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.university.portal.dto.CreateTaskDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "task")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;
    @Column(name = "task_name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "start_line")
    private Date startLine;
    @Column(name = "dead_line")
    private Date deadLine;

    @ElementCollection
    @CollectionTable(name = "tasks_files", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "file_uri")
    private Set<String> filesUri = new HashSet<>();

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @JsonBackReference
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private Set<TaskAnswer> taskAnswers = new HashSet<>();

    public Task(CreateTaskDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.startLine = dto.getStartLine();
        this.deadLine = dto.getDeadLine();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!Objects.equals(id, task.id)) return false;
        if (!Objects.equals(name, task.name)) return false;
        if (!Objects.equals(description, task.description)) return false;
        if (!Objects.equals(startLine, task.startLine)) return false;
        if (!Objects.equals(deadLine, task.deadLine)) return false;
        if (!Objects.equals(teacher, task.teacher)) return false;
        return Objects.equals(group, task.group);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startLine != null ? startLine.hashCode() : 0);
        result = 31 * result + (deadLine != null ? deadLine.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }
}
