package com.justcodeit.moyeo.study.application.post.exception;

import com.justcodeit.moyeo.study.application.exception.ErrorCode;
import com.justcodeit.moyeo.study.application.exception.MoyeoException;

public class PostCannotFoundException extends MoyeoException {
    public PostCannotFoundException() {
        super(ErrorCode.NOT_FOUND_POST);
    }
}