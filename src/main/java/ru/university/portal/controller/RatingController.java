package ru.university.portal.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateRatingDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.UpdateRatingDTO;
import ru.university.portal.model.Rating;
import ru.university.portal.service.RatingService;

@RestController
@RequestMapping("/rating")
@AllArgsConstructor
@Slf4j
public class RatingController {

    private final RatingService ratingService;
    private final AmqpTemplate amqpTemplate;

    @PostMapping("/create")
    public ResponseEntity<?> createRating(@RequestBody CreateRatingDTO dto) {
        try {
            Rating rating = ratingService.createRating(dto);
            amqpTemplate.convertAndSend("studentQueue", "Выставлена оценка по предмету \""
                    + rating.getSubject().getSubjectName() + "\"");
            return ResponseEntity.ok().body(rating);

        } catch (RuntimeException e) {
            log.error("Оценка студенту с id= " + dto.getStudentId() + " не выставлена. Error: "
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Оценка не выставлена. " +
                    "Error: " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("/update/{ratingId}/{teacherId}")
    public ResponseEntity<?> updateRating(@PathVariable Long ratingId,
                                          @PathVariable Long teacherId,
                                          @RequestBody UpdateRatingDTO dto) {
        try {
            ratingService.updateRating(ratingId, teacherId, dto);
            return ResponseEntity.ok().body(new MessageResponse("Оценка обновлена"));

        } catch (RuntimeException e) {
            log.error("Оценка с id= " + ratingId + " не обновлена. Error: "
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Оценка не обновлена. " +
                    "Error: " + e.getLocalizedMessage()));
        }
    }

    @GetMapping("/student/{taskId}/{studentId}")
    public ResponseEntity<?> findRatingForStudent(@PathVariable Long taskId,
                                                  @PathVariable Long studentId) {
        return ResponseEntity.ok().body(ratingService.findRatingForStudent(taskId, studentId));
    }

    @GetMapping("/teacher/{taskId}/{teacherId}")
    public ResponseEntity<?> findAllForTeacher(@PathVariable Long taskId,
                                               @PathVariable Long teacherId) {
        return ResponseEntity.ok().body(ratingService.findRatingsForTeacher(taskId, teacherId));
    }

    @GetMapping("/subject/{subjectId}/{studentId}")
    public ResponseEntity<?> findRatingsForSubject(@PathVariable Long subjectId,
                                                   @PathVariable Long studentId) {
        return ResponseEntity.ok().body(ratingService.findRatingsForSubject(subjectId, studentId));
    }
}
