package ru.university.portal.model;

import lombok.*;
import ru.university.portal.dto.CreateTaskDTO;

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
    @CollectionTable(name = "tasks_files", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "file_uri")
    private Set<String> fileUri;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskAnswer> taskAnswers;

    public Task(CreateTaskDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.startLine = dto.getStartLine();
        this.deadLine = dto.getDeadLine();
        this.fileUri = dto.getFileUri();
        this.teacher = dto.getTeacher();
        this.group = dto.getGroup();
    }
}
