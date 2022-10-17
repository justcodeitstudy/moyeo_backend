package com.justcodeit.moyeo.study.application.skill.exception;

import com.justcodeit.moyeo.study.application.exception.ErrorCode;
import com.justcodeit.moyeo.study.application.exception.MoyeoException;

public class SkillCannotFoundException extends MoyeoException {

    public SkillCannotFoundException() {
        super(ErrorCode.NOT_FOUND_SKILL);
    }
}
