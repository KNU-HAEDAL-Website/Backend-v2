package com.haedal.haedalweb.application.board.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantResponseDto {
    private Long participantId;

    private String userId;

    private String userName;
}
