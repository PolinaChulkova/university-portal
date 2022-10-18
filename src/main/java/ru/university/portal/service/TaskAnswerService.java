package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.TaskAnswerDTO;
import ru.university.portal.model.Task;
import ru.university.portal.model.TaskAnswer;
import ru.university.portal.repo.TaskAnswerRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskAnswerService {

    private final TaskService taskService;
    private final TaskAnswerRepo taskAnswerRepo;

    public void sendTaskAnswer(TaskAnswerDTO dto) {
        try {
            Task task = taskService.findTaskById(dto.getTaskId());
            TaskAnswer answer = new TaskAnswer();
            answer.setComment(dto.getComment());
            answer.setFileUri(dto.getFileUri());
            answer.setStudent(dto.getStudent());
            answer.setTask(task);

            task.getTaskAnswers().add(answer);

            taskService.saveTask(task);
            taskAnswerRepo.save(answer);

        } catch (RuntimeException e) {
            log.error("Ответ на задание не отправлен. {}" + e.getLocalizedMessage());
        }
    }

    public void updateTaskAnswer(Long taskAnswerId, TaskAnswerDTO dto) {
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
