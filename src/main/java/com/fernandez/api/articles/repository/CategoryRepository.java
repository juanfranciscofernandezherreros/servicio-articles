package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByLanguage(String acceptLanguage);

    Category findByName(String name);

    @Query(value = "SELECT count(*) FROM articles_categories WHERE articles_categories.categories_id=?1", nativeQuery = true)
    Long countTotalArticlesFromCategory(Category category);

}
