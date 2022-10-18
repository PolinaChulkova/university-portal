package ru.university.portal.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
@Getter@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;
    @Column(name = "group_name", unique = true)
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Subject> subjects = new ArrayList<>();
}
