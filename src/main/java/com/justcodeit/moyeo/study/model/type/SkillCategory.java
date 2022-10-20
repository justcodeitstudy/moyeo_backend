package com.justcodeit.moyeo.study.model.type;

import lombok.Getter;

@Getter
public enum SkillCategory {
    BACK_END("back-end", "백엔드"),
    FRONT_END("front-end", "프론트엔드"),
    MOBILE("mobile", "모바일"),
    DESIGN("design", "디자인"),
    DEVOPS("devops","데브옵스"),

    TESTING("testing", "테스팅"),
    LANGUAGE("language", "언어"),
    DATA("data", "데이터"),
    DATABASE("database","데이터베이스"),
    CO_WORKING_TOOL("co-working-tool", "협업도구"),

    ETC("etc", "기타");

    private String engWord;
    private String korWord;

    SkillCategory(String engWord, String korWord) {
        this.engWord = engWord;
        this.korWord = korWord;
    }
}
