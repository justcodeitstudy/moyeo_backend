package com.justcodeit.moyeo.study.model.post;

import lombok.Getter;

/**
 * 모집글의 진행 방식
 */
@Getter
public enum ProgressType {
    MIX("Online/OffLine","온라인/오프라인"),
    ONLINE("Online","온라인"),
    OFFLINE("OffLine","오프라인");

    private String engWord;
    private String korWord;

    ProgressType(String engWord, String korWord) {
        this.engWord = engWord;
        this.korWord = korWord;
    }
}
