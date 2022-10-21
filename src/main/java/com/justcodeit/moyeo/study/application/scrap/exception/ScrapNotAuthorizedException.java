package com.justcodeit.moyeo.study.application.scrap.exception;

import com.justcodeit.moyeo.study.application.exception.ErrorCode;
import com.justcodeit.moyeo.study.application.exception.MoyeoException;

public class ScrapNotAuthorizedException extends MoyeoException {

  public ScrapNotAuthorizedException() {
    super(ErrorCode.SCRAP_NOT_AUTHORIZED);
  }
}