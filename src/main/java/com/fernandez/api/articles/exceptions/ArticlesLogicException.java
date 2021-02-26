package com.fernandez.api.articles.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ArticlesLogicException extends ArticlesException{

    @Getter
    private final HttpStatus httpStatus;

    public ArticlesLogicException(final HttpStatus httpStatus , final String message){
        super(message);
        this.httpStatus = httpStatus;
    }

}
