package com.justcodeit.moyeo.study.persistence;

import lombok.Getter;

@Getter
public enum RecruitType {
    BACK_END("Back-End", "백엔드"),
    FRONT_END("Front-End", "프론트엔드"),
    DESIGN("Design", "디자인"),
    PRODUCT("Product", "기획"),
    ETC("etc", "기타");

    private String engWord;
    private String korWord;

    RecruitType(String engWord, String korWord) {
        this.engWord = engWord;
        this.korWord = korWord;
    }
}