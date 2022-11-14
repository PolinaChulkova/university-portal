package ru.university.portal.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "task_answer")
@Getter@Setter
@NoArgsConstructor
public class TaskAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_answer_id")
    private Long id;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date")
    private Date date = new Date();

    @ElementCollection
    @CollectionTable(name = "answers_files", joinColumns = @JoinColumn(name = "task_answer_id"))
    @Column(name = "file_uri")
    private Set<String> filesUri;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;

    public TaskAnswer(String comment, Student student, Task task) {
        this.comment = comment;
        this.student = student;
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskAnswer that = (TaskAnswer) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(comment, that.comment)) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(filesUri, that.filesUri)) return false;
        if (!Objects.equals(student, that.student)) return false;
        return Objects.equals(task, that.task);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (filesUri != null ? filesUri.hashCode() : 0);
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }
}
