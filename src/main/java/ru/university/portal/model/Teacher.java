package ru.university.portal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teacher")
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_num")
    private String phoneNum;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Subject> subjects;
}
