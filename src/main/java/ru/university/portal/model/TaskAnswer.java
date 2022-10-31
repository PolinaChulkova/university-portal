package ru.university.portal.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "task_answer")
@Getter
@Setter
@EqualsAndHashCode
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
    private Set<String> fileUri;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;

    public TaskAnswer(String comment, Set<String> fileUri, Student student, Task task) {
        this.comment = comment;
        this.fileUri = fileUri;
        this.student = student;
        this.task = task;
    }
}
