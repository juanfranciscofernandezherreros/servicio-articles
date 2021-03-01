package com.fernandez.api.articles.common;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class Messages {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Messages.class);
    private final MessageSource messageSource;

    private MessageSourceAccessor accessor;

    public Messages(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public @NotNull String get(final @NotNull String code) {
        return accessor.getMessage(code);
    }

}
