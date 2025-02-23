package com.haedal.haedalweb.application.post.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWithBoardResponseDto extends BasePostResponseDto {

	@Schema(description = "게시판 id")
	private Long boardId;

	@Schema(description = "게시판 생성자 아이디", example = "haedal12")
	private String boardUserId;

	@Schema(description = "게시판 생성자 이름", example = "조대성")
	private String boardUserName;

	@Schema(description = "활동 시작일", example = "yyyy-MM-dd (2024-07-24)")
	private LocalDate postActivityStartDate;

	@Schema(description = "활동 종료일", example = "yyyy-MM-dd (2024-07-24)")
	private LocalDate postActivityEndDate;
}
