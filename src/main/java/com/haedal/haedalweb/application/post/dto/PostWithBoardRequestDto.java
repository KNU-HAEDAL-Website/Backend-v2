package com.haedal.haedalweb.application.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWithBoardRequestDto extends BasePostRequestDto {
    @Schema(description = "활동 시작일", example = "yyyy-MM-dd (2024-07-24)")
    @NotNull(message = "활동 시작일은 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate postActivityStartDate;

    @Schema(description = "활동 종료일 (생략 가능)", example = "yyyy-MM-dd (2024-07-24)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate postActivityEndDate;
}
