package com.fernandez.api.articles.exceptions;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ErrorHandlingController {

    @ExceptionHandler(ArticlesLogicException.class)
    public ResponseEntity<ErrorMessage> logicException(final ArticlesLogicException exception, final WebRequest request) {
        return buildResponseEntityException(new ErrorMessage(exception.getHttpStatus(), exception.getMessage(), Objects.toString(exception.getMessage())));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> dataIntegrationViolation(final DataIntegrityViolationException exception, final WebRequest request) {
        return buildResponseEntityException(new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), Objects.toString(exception.getMessage())));
    }

    public ResponseEntity<ErrorMessage> buildResponseEntityException(final ErrorMessage error) {
        return new ResponseEntity<>(error, error.getStatusCode());
    }

}
