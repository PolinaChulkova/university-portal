package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.UpdateTaskDTO;
import ru.university.portal.model.Task;
import ru.university.portal.repo.TaskRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepo taskRepo;
    private final StudentService studentService;

    public void createTask(CreateTaskDTO dto) {

        if (!dto.getTeacher().getGroups().contains(dto.getGroup()))
            throw new RuntimeException("Невозможно создать задание для группы " + dto.getGroup().getName()
            + ", т.к. она закреплена за другим преподавателем");

        try {
            Task task = new Task(dto);
//            создать оповещение студентов группы
            taskRepo.save(task);

        } catch (RuntimeException e) {
            log.error("Задание " + dto.getName() + " не создано. {}"
                    + e.getLocalizedMessage());
        }
    }

    public void updateTask(Long taskId, UpdateTaskDTO dto) {
        try {
            Task task = findTaskById(taskId);

            if (!task.getTeacher().equals(dto.getTeacher()))
                throw new RuntimeException("Невозможно обновить задание" + dto.getName()
                        + ", т.к. оно создано другим преподавателем");

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

    public Task findTaskByIdForStudent(Long taskId, Long studentId) {
        Long groupId = studentService.findStudentById(studentId).getGroup().getId();
        return taskRepo.findByIdAndGroup_Id(taskId, groupId).orElseThrow(()
                -> new RuntimeException("Задание с id=" + taskId + "не найдено."));
    }

    public Task findTaskByIdForTeacher(Long taskId, Long teacherId) {
        return taskRepo.findByIdAndTeacher_Id(taskId, teacherId).orElseThrow(()
                -> new RuntimeException("Задание с id=" + taskId + "не найдено."));
    }

    public Page<Task> findAllTeacherTasks(Long teacherId, Pageable pageable) {
        return taskRepo.findAllByTeacher_Id(teacherId, pageable);
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
