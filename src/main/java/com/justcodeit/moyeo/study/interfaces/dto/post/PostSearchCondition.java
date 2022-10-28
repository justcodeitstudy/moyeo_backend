package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
    @ArraySchema(schema = @Schema(type = "Long",name = "skillId",description = "기술스택 ID"), maxItems = 10, minItems = 1)
    private List<Long> skillList;
}
