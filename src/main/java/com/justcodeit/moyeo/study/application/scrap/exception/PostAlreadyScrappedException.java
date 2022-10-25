package com.justcodeit.moyeo.study.application.scrap.exception;

import com.justcodeit.moyeo.study.application.exception.ErrorCode;
import com.justcodeit.moyeo.study.application.exception.MoyeoException;

public class PostAlreadyScrappedException extends MoyeoException {

  public PostAlreadyScrappedException() {
    super(ErrorCode.POST_ALREADY_SCRAPPED);
  }

  public PostAlreadyScrappedException(String message) {
    super(message, ErrorCode.POST_ALREADY_SCRAPPED);
  }
}