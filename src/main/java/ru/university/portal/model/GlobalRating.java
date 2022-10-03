package ru.university.portal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "global_rating")
@Getter
@Setter
public class GlobalRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "global_rating_id")
    private Long id;
    @Column(name = "mark")
    private Short mark;
    @Column(name = "comment")
    private String comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task")
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;

}
