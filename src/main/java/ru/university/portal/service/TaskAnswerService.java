package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.CreateTaskAnswerDTO;
import ru.university.portal.dto.UpdateTaskAnswerDTO;
import ru.university.portal.model.Task;
import ru.university.portal.model.TaskAnswer;
import ru.university.portal.repo.TaskAnswerRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskAnswerService {

    private final TaskService taskService;
    private final TaskAnswerRepo taskAnswerRepo;

    public TaskAnswer getTaskAnswerForStudent(Long taskId, Long studentId) {
        return taskAnswerRepo.findByTaskIdAndStudentId(taskId, studentId).orElseThrow(() ->
                new RuntimeException("Ваш ответ не найден"));
    }

    public List<TaskAnswer> getTaskAnswersForTeacher(Long taskId, Long teacherId) {
        return taskAnswerRepo.findByTaskIdAndTeacherId(taskId, teacherId);
    }

    public void sendTaskAnswer(CreateTaskAnswerDTO dto) {
        try {
//            возможна ошибка
            if (getTaskAnswerForStudent(dto.getTaskId(), dto.getStudent().getId()) == null) {

                Task task = taskService.findTaskById(dto.getTaskId());
                TaskAnswer answer = new TaskAnswer();
                answer.setComment(dto.getComment());
                answer.setFileUri(dto.getFileUri());
                answer.setStudent(dto.getStudent());
                answer.setTask(task);

                task.getTaskAnswers().add(answer);

                taskService.saveTask(task);
                taskAnswerRepo.save(answer);

            }
        } catch (RuntimeException e) {
            log.error("Ответ на задание не отправлен. {}" + e.getLocalizedMessage());
        }
    }

    public void updateTaskAnswer(Long taskAnswerId, UpdateTaskAnswerDTO dto) {
        try {
            TaskAnswer answer = findTaskAnswerById(taskAnswerId);
            answer.setComment(dto.getComment());
            answer.setFileUri(dto.getFileUri());
            taskAnswerRepo.save(answer);
        } catch (Exception e) {
            log.error("Ваш ответ не обновлён. {}" + e.getLocalizedMessage());
        }
    }

    public TaskAnswer findTaskAnswerById(Long taskAnswerId) {
        return taskAnswerRepo.findById(taskAnswerId).orElseThrow(() ->
                new RuntimeException("Ваш ответ не существует"));
    }
}
