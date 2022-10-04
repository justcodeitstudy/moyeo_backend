package com.justcodeit.moyeo.study.persistence;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum JobType {
    PLAN("PLAN", "기획"),
    DESIGN("DESIGN", "디자인"),
    FRONTEND("FRONTEND", "프론트엔드"),
    BACKEND("BACKEND", "백엔드"),
    ETC("ETC", "기타");

    private String engWord;
    private String korWord;

    JobType(String engWord, String korWord) {
        this.engWord = engWord;
        this.korWord = korWord;
    }

    public String getEngWord() {
        return engWord;
    }

    public String getKorWord() {
        return korWord;
    }

    @JsonCreator
    public static JobType getJobTypeFromValue(String value) {
        try {
            return JobType.valueOf(value);
        } catch (Exception e) {
            //TODO valid 한 값 아닐 시 예외
            return null;
        }
    }
}
