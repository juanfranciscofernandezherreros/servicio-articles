package com.fernandez.api.articles.validator.title;

import com.fernandez.api.articles.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<Title, String>
{

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        return articleRepository.existsByTitle(value);
    }
}
