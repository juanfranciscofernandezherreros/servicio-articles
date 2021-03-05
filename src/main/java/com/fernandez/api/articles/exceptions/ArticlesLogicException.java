package com.fernandez.api.articles.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ArticlesLogicException extends ArticlesException {

    private static final long serialVersionUID = 1L;

    @Getter
    private final HttpStatus httpStatus;

    public ArticlesLogicException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
