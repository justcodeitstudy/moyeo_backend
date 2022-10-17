package com.justcodeit.moyeo.study.model.post;

import lombok.Getter;

/**
 * 모집 상태
 */
@Getter
public enum RecruitStatus {
    RECRUITING("Recruiting", "모집중"),
    COMPLETE("Recruit Complete","모집완료"),
    FINISH("Recruit Finished","모집종료");

    private String korWord;
    private String engWord;

    RecruitStatus(String engWord, String korWord) {
        this.engWord = engWord;
        this.korWord = korWord;
    }
}
