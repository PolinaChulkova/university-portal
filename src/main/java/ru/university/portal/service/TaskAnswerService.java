package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.TaskAnswerDTO;
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
            TaskAnswer answer = new TaskAnswer();
            answer.setComment(dto.getComment());
            answer.setFileUri(dto.getFileUri());
            answer.setStudent(dto.getStudent());
            answer.setTask(taskService.findTaskById(dto.getTaskId()));

            taskAnswerRepo.save(answer);

        } catch (RuntimeException e) {
            log.error("Ответ на задание не отправлен. {}" + e.getLocalizedMessage());
        }
    }
}
