package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "Request에서 넘어오는 검색조건 객체")
@Setter
@Getter
public class PostSearchCondition {
    @Schema(description = "제목")
    private String title;
    @Schema(description = "모집상태", defaultValue = "RECRUITING")
    private RecruitStatus status;
    @Parameter(description = "기술스택 ID 배열", example = "[1, 6, 11, 55]", content = @Content(array = @ArraySchema(schema = @Schema(type = "int",format = "int64"))))
    private List<Long> skillList;
}
