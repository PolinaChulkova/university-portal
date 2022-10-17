package ru.university.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.university.portal.model.TaskAnswer;

@Repository
public interface TaskAnswerRepo extends JpaRepository<TaskAnswer, Long> {

}
