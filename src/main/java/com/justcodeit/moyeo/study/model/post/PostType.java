package com.justcodeit.moyeo.study.model.post;

import lombok.Getter;

/**
 * 모집글의 분류
 */
@Getter
public enum PostType {
    STUDY("Study","스터디"),
    SIDE_PROJECT("Side Project","사이드 프로젝트"),
    TOY_PROJECT("Toy Project","토이 프로젝트"),
    PROJECT("Project","프로젝트");

    private String engWord;
    private String korWord;

    PostType(String engWord, String korWord) {
        this.engWord = engWord;
        this.korWord = korWord;
    }
}
