package ru.university.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.university.portal.model.TaskAnswer;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAnswerRepo extends JpaRepository<TaskAnswer, Long> {

    Optional<TaskAnswer> findById(Long id);

    Optional<TaskAnswer> findByTaskIdAndStudentId(Long taskId, Long studentId);

    @Query(value = "SELECT t FROM TaskAnswer t WHERE t.task.id = ?1 AND t.task.teacher.id = ?2")
    List<TaskAnswer> findByTaskIdAndTeacherId(Long taskId, Long teacherId);
}
