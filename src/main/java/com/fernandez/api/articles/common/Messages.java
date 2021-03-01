package com.fernandez.api.articles.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Messages {

    private final MessageSource messageSource;

    private MessageSourceAccessor accessor;

    public String get(final String code) {
        return accessor.getMessage(code);
    }

}
