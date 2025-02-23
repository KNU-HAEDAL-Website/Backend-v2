package com.haedal.haedalweb.application.board.mapper;

import com.haedal.haedalweb.application.board.dto.BoardResponseDto;
import com.haedal.haedalweb.domain.board.model.Board;

public class BoardMapper {
	private BoardMapper() {
	}

	public static BoardResponseDto toDto(String imageUrl, Board board) {
		return BoardResponseDto.builder()
			.activityId(board.getActivity().getId())
			.boardId(board.getId())
			.boardName(board.getName())
			.boardImageUrl(imageUrl)
			.userId(board.getUser().getId())
			.userName(board.getUser().getName())
			.boardIntro(board.getIntro())
			.participants(
				ParticipantMapper.toDtos(board.getParticipants())) // List<Participants>로 List<participantDTO> 만들기
			.build();
	}
}
