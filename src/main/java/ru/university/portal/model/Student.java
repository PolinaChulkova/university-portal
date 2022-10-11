package ru.university.portal.model;

import lombok.*;
import ru.university.portal.dto.StudentDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
@Getter@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_num")
    private String phoneNum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Rating> rating;

    public Student(StudentDTO dto, Group group) {
        this.fullName = dto.getFullName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.phoneNum = dto.getPhoneNum();
        this.group = group;
    }
}
