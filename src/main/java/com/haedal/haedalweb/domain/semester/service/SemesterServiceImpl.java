package com.haedal.haedalweb.domain.semester.service;

import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.web.semester.dto.SemesterResponseDto;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;

    public List<SemesterResponseDto> getAllSemesterDTOs() {
        List<Semester> semesters = semesterRepository.findAll(Sort.by("name"));

        return semesters.stream()
                .map(this::convertToSemesterDTO)
                .collect(Collectors.toList());
    }

    private SemesterResponseDto convertToSemesterDTO(Semester semester) {
        return SemesterResponseDto.builder()
                .semesterId(semester.getId())
                .semesterName(semester.getName())
                .build();
    }
}
