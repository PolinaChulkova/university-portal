package ru.university.portal.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "task")
@Getter@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;
    @Column(name = "task_name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "start_line")
    private Date startLine;
    @Column(name = "dead_line")
    private Date deadLine;

    @ElementCollection
    @CollectionTable(name = "files", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "file")
    private Set<String> files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group")
    private Group group;

}
