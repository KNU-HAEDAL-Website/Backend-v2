package com.haedal.haedalweb.application.board.mapper;

import com.haedal.haedalweb.domain.board.model.Participant;
import com.haedal.haedalweb.application.board.dto.ParticipantResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ParticipantMapper {
    private ParticipantMapper() {
    }

    public static ParticipantResponseDto toDto(Participant participant) {
        return ParticipantResponseDto.builder()
                .participantId(participant.getId())
                .userId(participant.getUser().getId())
                .userName(participant.getUser().getName())
                .build();
    }

    public static List<ParticipantResponseDto> toDtos(List<Participant> participants) {
        return participants.stream()
                .map(ParticipantMapper::toDto)
                .collect(Collectors.toList());
    }
}
