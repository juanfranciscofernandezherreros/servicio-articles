package com.fernandez.api.articles.common;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class Messages {

    private MessageSourceAccessor accessor;

    public @NotNull String get ( final @NotNull String code ) {

        return accessor.getMessage ( code );
    }

}
