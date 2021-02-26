package com.fernandez.api.articles.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorMessage {

    private final HttpStatus statusCode;
    private final String message;
    private final String description;

    public ErrorMessage(HttpStatus statusCode, String message, String description) {
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }

}
