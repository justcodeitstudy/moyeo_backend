package com.justcodeit.moyeo.study.model.post;

public enum PostStatus {
    RECRUITING("Recruiting", "모집중"),
    COMPLETE("Recruit Complete","모집완료"),
    FINISH("Recruit Finished","모집종료");

    private String korWord;
    private String engWord;

    PostStatus(String engWord, String korWord) {
        this.engWord = engWord;
        this.korWord = korWord;
    }
}