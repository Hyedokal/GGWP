package com.ggwp.global.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomException.CustomExceptionResponse> handleValidationException(
      MethodArgumentNotValidException ex) {

    List<String> errors = ex.getBindingResult().getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .filter(Objects::nonNull)
        .toList();

    CustomException.CustomExceptionResponse errorResponse = new CustomException.CustomExceptionResponse(
        HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
        String.join(", ", errors));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

}
