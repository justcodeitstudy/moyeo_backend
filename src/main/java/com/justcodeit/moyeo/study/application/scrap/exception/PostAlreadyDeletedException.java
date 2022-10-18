package com.justcodeit.moyeo.study.application.scrap.exception;

import com.justcodeit.moyeo.study.application.exception.ErrorCode;
import com.justcodeit.moyeo.study.application.exception.MoyeoException;

public class PostAlreadyDeletedException extends MoyeoException {

  public PostAlreadyDeletedException() {
    super(ErrorCode.POST_ALREADY_DELETED);
  }
}