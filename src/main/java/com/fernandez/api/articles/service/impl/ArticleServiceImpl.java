package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.model.auditable.Audit;
import com.fernandez.api.articles.repository.ArticleRepository;
import com.fernandez.api.articles.repository.CountCommentsBlogRepository;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.service.CategoryService;
import com.fernandez.api.articles.service.TagService;
import com.fernandez.api.articles.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserService userService;

    private final CountCommentsBlogRepository countCommentsBlogRepository;

    private final CategoryService categoryService;

    private final TagService tagService;

    private final Messages messages;

    @Override
    public ArticleDTO save(final ArticleDTO articleDTO) {
        log.debug("[ArticleServiceImpl][save] articleDTO={}", articleDTO);
        ModelMapper modelMapper = new ModelMapper();
        if (articleDTO.getCategories().size() > 0) {
            articleDTO.setUser(userService.findByUsername(articleDTO.getUser().getUsername()));
            articleDTO.setCategories(categoryService.categoryDTOList(articleDTO));
            if (articleDTO.getTags().size() > 0) {
                articleDTO.setTags(tagService.tagDTOList(articleDTO));
            }
            Article article = modelMapper.map(articleDTO, Article.class);
            return modelMapper.map(articleRepository.save(article), ArticleDTO.class);
        } else {
            throw new ArticlesLogicException(HttpStatus.BAD_REQUEST, PropertiesConstant.ONE_CATEGORY);
        }
    }

    @Override
    public void deleteArticleById(final Long articleId) {
        articleRepository.delete(articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND))));
    }

    @Override
    public ArticleDTO update(final ArticleDTO articleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        if (articleDTO.getCategories().size() > 0) {
            articleRepository.findById(articleDTO.getId())
                    .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND)));
            Article article = modelMapper.map(articleDTO, Article.class);
            Audit audit = new Audit();
            DateFormat sourceFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date createdOnDate = null;
            try {
                createdOnDate = sourceFormat.parse(articleDTO.getAuditDTO().getCreatedOn());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            audit.setCreatedOn(createdOnDate);
            article.setAudit(audit);
            return modelMapper.map(articleRepository.save(article), ArticleDTO.class);
        } else {
            throw new ArticlesLogicException(HttpStatus.BAD_REQUEST, PropertiesConstant.ONE_CATEGORY);
        }
    }

    @Override
    public Page<ArticleDTO> findAllArticles(final String acceptLanguage,
                                            final String name,
                                            final List<String> tag,
                                            final List<String> categories,
                                            final Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        Page<ArticleDTO> articleList = null;
        if (Objects.isNull(name) && Objects.isNull(categories) && Objects.isNull(tag)) {
            articleList = articleRepository.findAllByLanguage(acceptLanguage, pageable)
                    .map(article -> mapFromEntityToDto(article));
        }
        if (Objects.nonNull(name)) {
            articleList = articleRepository.findArticleByLanguageAndTitle(acceptLanguage, name, pageable)
                    .map(article -> mapFromEntityToDto(article));
        }
        if (Objects.nonNull(categories)) {
            articleList = articleRepository.findByCategoriesIn(findAllCategoriesById(categories), pageable)
                    .map(article -> mapFromEntityToDto(article));
        }
        if (Objects.nonNull(tag)) {
            articleList = articleRepository.findByTagsIn(findAllTagsById(tag), pageable)
                    .map(article -> mapFromEntityToDto(article));
        }
        return articleList;
    }

    @Override
    public ArticleDTO findArticleBySlugOrId(String slug, Long articleId) {
        ArticleDTO articleDTO = null;
        if(Objects.nonNull(slug)){
            articleDTO = findArticleBySlug(slug);
        }
        if(Objects.nonNull(articleId)){
            articleDTO = findArticleById(articleId);
        }
        return articleDTO;
    }

    private List<Category> findAllCategoriesById(List<String> categories) {
        return categories.stream()
                .map(category -> categoryService.findCategoryById(Long.valueOf(category)))
                .collect(Collectors.toList());
    }

    private List<Tag> findAllTagsById(List<String> tags) {
        return tags.stream()
                .map(tag -> tagService.findTagById(Long.valueOf(tag)))
                .collect(Collectors.toList());
    }


    private ArticleDTO findArticleBySlug(final String slug) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(articleRepository.findArticleBySlug(slug)
                        .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND)))
                , ArticleDTO.class);
    }

    private ArticleDTO findArticleById(final Long articleId) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(
                articleRepository.findById(articleId)
                        .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND)))
                , ArticleDTO.class);
    }

    private ArticleDTO mapFromEntityToDto(Article article) {
        ModelMapper modelMapper = new ModelMapper();
        ArticleDTO articleDto = modelMapper.map(article, ArticleDTO.class);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(article.getAudit().getCreatedOn());
        articleDto.setCreatedDate(formattedDate);
        articleDto.setTotalComments(countCommentsBlogRepository.countCommentsFromArticle(article.getId()));
        return articleDto;
    }
}
