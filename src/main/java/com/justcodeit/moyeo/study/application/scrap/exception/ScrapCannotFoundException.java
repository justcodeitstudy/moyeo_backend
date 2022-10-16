package com.justcodeit.moyeo.study.application.scrap.exception;

import com.justcodeit.moyeo.study.application.exception.ErrorCode;
import com.justcodeit.moyeo.study.application.exception.MoyeoException;

public class ScrapCannotFoundException extends MoyeoException {

  public ScrapCannotFoundException() {
    super(ErrorCode.NOT_FOUND_SCRAP);
  }

  public ScrapCannotFoundException(String message) {
    super(message, ErrorCode.NOT_FOUND_SCRAP);
  }
}