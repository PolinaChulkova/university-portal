package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.UpdateTaskDTO;
import ru.university.portal.model.Task;
import ru.university.portal.repo.GroupRepo;
import ru.university.portal.repo.TaskRepo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepo taskRepo;
    private final GroupRepo groupRepo;
    private final FileService fileService;
    private final TeacherService teacherService;

    public Task createTask(CreateTaskDTO dto) {
            if (groupRepo.findByTeacherIdAndGroupId(dto.getTeacherId(), dto.getGroupId()) == null)
                throw new RuntimeException("Невозможно создать задание для группы, " +
                        "т.к. она закреплена за другим преподавателем");

            Task task = new Task(dto);
            taskRepo.save(task);
            return task;
    }

    public void uploadFilesToTask(Long taskId, MultipartFile[] files) {
        Task task = findTaskById(taskId);
        task.getFilesUri().addAll(Arrays.stream(files)
                .map(fileService::uploadFile).collect(Collectors.toList()));
        taskRepo.save(task);
    }

    public void deleteFileFromTask(Long taskId, String fileUri) throws RuntimeException{
        Task task = findTaskById(taskId);
        task.getFilesUri().remove(fileUri);
        taskRepo.save(task);
    }

    public void updateTask(Long taskId, UpdateTaskDTO dto) {
            Task task = findTaskById(taskId);

            if (!task.getTeacher().equals(teacherService.findTeacherById(dto.getTeacherId())))
                throw new RuntimeException("Невозможно обновить задание" + dto.getName()
                        + ", т.к. оно создано другим преподавателем");

            task.setName(dto.getName());
            task.setDescription(dto.getDescription());
            task.setDeadLine(dto.getDeadLine());

            taskRepo.save(task);
    }

    public List<Task> findAllTasksBySubjectForStudent(Long subjectId, Long studentId) {
        return taskRepo.findBySubjectIdAndStudentId(subjectId, studentId);
    }

    public Task findTaskByIdForStudent(Long taskId, Long studentId) {
        return taskRepo.findByIdAndStudentId(taskId, studentId).orElseThrow(()
                -> new RuntimeException("Задание с id=" + taskId + " недоступно."));
    }

    public Task findTaskByIdForTeacher(Long taskId, Long teacherId) {
        return taskRepo.findByIdAndTeacher_Id(taskId, teacherId).orElseThrow(()
                -> new RuntimeException("Задание с id=" + taskId + " недоступно."));
    }

    public Page<Task> findAllTeacherTasks(Long teacherId, Pageable pageable) {
        return taskRepo.findAllByTeacher_Id(teacherId, pageable);
    }

    public Task findTaskById(Long taskId) {
        return taskRepo.findById(taskId).orElseThrow(()
                -> new RuntimeException("Задание с id=" + taskId + " не найдено."));
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
