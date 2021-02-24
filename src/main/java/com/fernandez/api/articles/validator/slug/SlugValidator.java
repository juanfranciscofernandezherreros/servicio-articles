package com.fernandez.api.articles.validator.slug;

import com.fernandez.api.articles.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SlugValidator implements ConstraintValidator<Slug, String>
{

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        return articleRepository.existsBySlug(value);
    }
}
