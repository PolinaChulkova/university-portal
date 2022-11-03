package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.university.portal.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t FROM Task t JOIN t.teacher.subjects s JOIN t.group.students st " +
            "WHERE s.id = ?1 AND st.id = ?2")
    List<Task> findBySubjectIdAndStudentId(Long subjectId, Long studentId);

    @Query(value = "SELECT t FROM Task t JOIN t.group.students s WHERE t.id = ?1 AND s.id = ?2")
    Optional<Task> findByIdAndStudentId(Long id, Long studentId);

    Page<Task> findAllByTeacher_Id(Long teacherId, Pageable pageable);

    Optional<Task> findByIdAndTeacher_Id(Long id, Long teacherId);

    Page<Task> findAll(Pageable pageable);

    Optional<Task> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);
}
