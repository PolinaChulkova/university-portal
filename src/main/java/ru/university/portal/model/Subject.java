package ru.university.portal.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subject")
@Getter@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;
    @Column(name = "subject_name")
    private String subjectName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "subject_group",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subjects")
    private Teacher teacher;
}
