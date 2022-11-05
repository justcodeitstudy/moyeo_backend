package com.justcodeit.moyeo.study.model.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 모집글의 분류
 */
@Schema(description = "모집글의 종류")
@Getter
public enum PostType {
    STUDY("Study","스터디"),
    PROJECT("Project","프로젝트");

    private String engWord;
    private String korWord;

    PostType(String engWord, String korWord) {
        this.engWord = engWord;
        this.korWord = korWord;
    }
}
