package com.haedal.haedalweb.domain.semester.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.exception.BusinessException;
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

    @Override
    public List<SemesterResponseDto> getAllSemesterDTOs() {
        List<Semester> semesters = semesterRepository.findAll(Sort.by("name"));

        return semesters.stream()
                .map(this::convertToSemesterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Semester findSemesterById(Long semesterId) {
        return semesterRepository.findById(semesterId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_SEMESTER_ID));
    }


    private SemesterResponseDto convertToSemesterDTO(Semester semester) {
        return SemesterResponseDto.builder()
                .semesterId(semester.getId())
                .semesterName(semester.getName())
                .build();
    }
}
