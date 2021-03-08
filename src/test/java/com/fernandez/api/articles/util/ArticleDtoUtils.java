package com.fernandez.api.articles.util;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.User;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleDtoUtils {

    public static ArticleDTO mockArticleDtoObject() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("kfh1992");
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Categoria1");
        categoryDTO.setLanguage("es-ES");
        categoryDTO.setSlug("Categoria1-Slug");
        categoryDTOList.add(categoryDTO);
        categoryDTOList.add(categoryDTO);
        categoryDTOList.add(categoryDTO);
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle("Title");
        articleDTO.setSlug("Slug");
        articleDTO.setDescription("Description");
        articleDTO.setContent("Content");
        articleDTO.setMainImage("Image");
        articleDTO.setLanguage("es-ES");
        articleDTO.setUser(userDTO);
        articleDTO.setCategories(categoryDTOList);
        return articleDTO;
    }

    public static Optional<Article> mockArticleOptional(){
        return Optional.of(mockArticleObject());
    }

    public static Article mockArticleObject() {
        User user = new User();
        Article  article = new Article();
        article.setTitle("Title");
        article.setSlug("Slug-1");
        article.setDescription("Description");
        article.setContent("Content");
        article.setMainImage("Image");
        article.setLanguage("es-ES");
        article.setUser(user);
        return article;
    }

    public static ArticleDTO mockArticleDtoWithTagsObject() {
       List<TagDTO> tagsList = new ArrayList<TagDTO>();
       TagDTO tagDTO = new TagDTO();
       tagDTO.setName("Tag1");
       tagDTO.setLanguage("es-ES");
       tagDTO.setSlug("Tag1-Slug");
       tagsList.add(tagDTO);
       ArticleDTO articleDto = mockArticleDtoObject();
       articleDto.setTags(tagsList);
       return articleDto;
    }

    public static Page<Article> mockPage() {
        Page<Article> articles = Mockito.mock(Page.class);
        return articles;
    }
}
