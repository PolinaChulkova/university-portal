package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateRatingDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.service.RatingService;
import ru.university.portal.service.SubjectService;
import ru.university.portal.service.TeacherService;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final RatingService ratingService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;


}
