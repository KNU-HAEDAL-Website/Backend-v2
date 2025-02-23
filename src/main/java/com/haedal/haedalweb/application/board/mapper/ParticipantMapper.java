package com.haedal.haedalweb.application.board.mapper;

import java.util.List;

import com.haedal.haedalweb.application.board.dto.ParticipantResponseDto;
import com.haedal.haedalweb.domain.board.model.Participant;

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
			.toList();
	}
}
