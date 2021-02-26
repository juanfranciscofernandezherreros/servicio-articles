package com.fernandez.api.articles.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Locale;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Messages {

    private final MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, new Locale("es", "ES"));
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }

}
