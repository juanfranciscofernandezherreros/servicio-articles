package com.fernandez.api.articles.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

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
