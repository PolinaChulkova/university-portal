package ru.university.portal.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@Getter@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long id;
    @Column(name = "mark")
    private Short mark;
    @Column(name = "comment")
    private String comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    public Rating(Short mark, String comment, Task task, Subject subject, Student student) {
        this.mark = mark;
        this.comment = comment;
        this.task = task;
        this.subject = subject;
        this.student = student;
    }
}
