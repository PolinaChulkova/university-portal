package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.UpdateTaskDTO;
import ru.university.portal.model.Task;
import ru.university.portal.model.TaskAnswer;
import ru.university.portal.repo.TaskRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepo taskRepo;

    private List<TaskAnswer> getTaskAnswers(Long taskId) {
        return findTaskById(taskId).getTaskAnswers();
    }

    public void createTask(CreateTaskDTO dto) {
        try {
            Task task = new Task(dto);
            taskRepo.save(task);

        } catch (RuntimeException e) {
            log.error("Задание " + dto.getName() + " не создано. {}"
                    + e.getLocalizedMessage());
        }
    }

    public void updateTask(Long taskId, UpdateTaskDTO dto) {
        try {
            Task task = findTaskById(taskId);

//       обновления можно сделать через query в repository
            task.setName(dto.getName());
            task.setDescription(dto.getDescription());
            task.setDeadLine(dto.getDeadLine());
            task.setFileUri(dto.getFileUri());

            taskRepo.save(task);

        } catch (RuntimeException e) {
            log.error("Задание " + dto.getName() + " не обновлёно. {}"
                    + e.getLocalizedMessage());
        }
    }

    public Task findTaskById(Long taskId) {
        return taskRepo.findById(taskId).orElseThrow(()
                -> new RuntimeException("Задание с id=" + taskId + "не найдено."));
    }

    public void deleteTaskById(Long taskId) {
        if (taskRepo.existsById(taskId)) taskRepo.deleteById(taskId);
        else throw new RuntimeException("Задание с id=" + taskId + "не существует.");
    }

    public void saveTask(Task task) {
        try {
            taskRepo.save(task);

        } catch (RuntimeException e) {
            log.error("Задание " + task.getName() + " не сохранёно. {}", e.getLocalizedMessage());
        }
    }
}
