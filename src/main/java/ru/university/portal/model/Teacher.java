package ru.university.portal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.university.portal.dto.TeacherDTO;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teacher")
@Getter@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements UserDetails {
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

    @Enumerated
    @Column(name = "role")
    private Role role;

    @JsonBackReference
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teachers_subjects",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teachers_groups",
    joinColumns = @JoinColumn(name = "teacher_id"),
    inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Collection<Group> groups;

    public Teacher(TeacherDTO dto) {
        this.fullName = dto.getFullName();
        this.email = dto.getEmail();
        this.phoneNum = dto.getPhoneNum();
        this.role = Role.valueOf(dto.getRole());
        this.academicDegree = dto.getAcademicDegree();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
