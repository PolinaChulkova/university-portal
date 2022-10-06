package ru.university.portal.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.repo.GroupRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepo groupRepo;


}
