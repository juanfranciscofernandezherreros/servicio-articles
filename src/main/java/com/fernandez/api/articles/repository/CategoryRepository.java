package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.dto.CategoryDto;
import com.fernandez.api.articles.model.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @NotNull CategoryDto findByName(String name);
}
