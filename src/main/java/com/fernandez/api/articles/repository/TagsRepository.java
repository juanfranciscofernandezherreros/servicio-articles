package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long> {

    Tag findByNameAndLanguage(String name, String language);
    Page<Tag> findAllByLanguage(String acceptLanguage, Pageable pageable);
}
