package com.fernandez.api.articles.exceptions;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;


@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ErrorHandlingController {

    @ExceptionHandler(ArticlesLogicException.class)
    public @NotNull ResponseEntity<ErrorMessage> logicException(final @NotNull ArticlesLogicException exception) {
        return buildResponseEntityException(new ErrorMessage(exception.getHttpStatus(),
                exception.getMessage(), Objects.toString(exception.getMessage())));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public @NotNull ResponseEntity<ErrorMessage> dataIntegrationViolation(final @NotNull DataIntegrityViolationException exception) {
        return buildResponseEntityException(new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), Objects.toString(exception.getMessage())));
    }

    public @NotNull ResponseEntity<ErrorMessage> buildResponseEntityException(final @NotNull ErrorMessage error) {
        return new ResponseEntity<>(error, error.getStatusCode());
    }

}
