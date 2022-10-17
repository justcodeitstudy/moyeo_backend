package com.justcodeit.moyeo.study.application.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

  SYSTEM_EXCEPTION(500, "S000", "Internal Server Error"),
  NOT_FOUND_HANDLER(404, "S404", "404 NOT FOUND"),

  INVALID_INPUT_VALUE(400, "B001", "Invalid Input Value"),
  METHOD_NOT_ALLOWED(405, "B002", "Method not allowed"),
  HANDLE_ACCESS_DENIED(403, "B006", "Access is Denied"),
  INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
  INVALID_TYPE_VALUE(400, "C005", "Invalid Type Value"),

  NOT_FOUND_USER(404, "U001", "No such User"),


  NOT_FOUND_SCRAP(404, "R001", "No such Scrap"),

  JWT_DECODE_FAILURE(500, "J001", "JWT cannot be decoded"),
  JWT_ENCODE_FAILURE(500, "J002", "DTO encode failure"),

  FILE_FETCH_FAILURE(500, "S001", "File fetch failure (from storage)"),

  ;
  private final String code;
  private final String message;
  private int status;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }
}