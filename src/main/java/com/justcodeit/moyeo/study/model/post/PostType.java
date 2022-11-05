package com.justcodeit.moyeo.study.model.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 모집글의 분류
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(description = "모집글의 종류")
@Getter
public enum PostType {
    PROJECT("Project","프로젝트"),
    STUDY("Study","스터디");

    private String engWord;
    private String value;

    PostType(String engWord, String value) {
        this.engWord = engWord;
        this.value = value;
    }
/*    @JsonCreator
    public static PostType forValues(@JsonProperty("value") String value) {
        return Arrays.stream(PostType.values())
                .filter(postType -> postType.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(PostInputInvalidType::new);
    }*/
}
