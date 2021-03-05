package com.fernandez.api.articles.exceptions;

public class ArticlesException extends RuntimeException  {

    private static final long serialVersionUID = 1L;

    public ArticlesException(String message) {
        super(message);
    }

}
