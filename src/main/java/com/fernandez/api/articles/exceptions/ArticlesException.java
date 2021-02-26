package com.fernandez.api.articles.exceptions;

public class ArticlesException  extends RuntimeException{

    public ArticlesException (final String message){
        super(message);
    }

    public ArticlesException (final String message , final Throwable throwable){
        super(message,throwable);
    }
}
