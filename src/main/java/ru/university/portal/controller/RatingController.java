package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateRatingDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.service.RatingService;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<?> createRating(@RequestBody CreateRatingDTO dto) {
        ratingService.createRating(dto);
        return ResponseEntity.ok().body(new MessageResponse("Выставлена оценка"));
    }

    @GetMapping("/{studentId}/{subjectId}")
    public ResponseEntity<?> findByStudentIdAndSubjectId(@PathVariable Long studentId,
                                                         @PathVariable Long subjectId) {
        return ResponseEntity.ok().body(ratingService.findRatingByStudentIdAndSubjectId(studentId, subjectId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> findByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok().body(ratingService.findRatingByTaskId(taskId));
    }
}
