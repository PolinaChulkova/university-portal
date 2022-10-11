package ru.university.portal.model;

import lombok.*;
import ru.university.portal.dto.TeacherDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teacher")
@Getter@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_num")
    private String phoneNum;
    @Column(name = "academic_degree")
    private String academicDegree;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Subject> subjects;

    public Teacher(TeacherDTO dto) {
        this.fullName = dto.getFullName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.phoneNum = dto.getPhoneNum();
        this.academicDegree = dto.getAcademicDegree();
    }
}
