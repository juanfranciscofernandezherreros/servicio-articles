package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long> {

    Tag findByNameAndLanguage(String name, String language);
}
