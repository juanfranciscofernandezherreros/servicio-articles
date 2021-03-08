package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ComentariosDTO;
import com.fernandez.api.articles.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlMapping.ROOT, produces = {APPLICATION_JSON_VALUE})
public class ComentariosController {

    private final ComentarioService comentarioService;

    @GetMapping(UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.COMMENTS)
    public List<ComentariosDTO> findAllCommentsFromArticle(@RequestParam() final Long articleId) {
        log.info("[ComentariosController][findAllCommentsFromArticle] comentariosDTO={}", articleId);
        return comentarioService.findAllComentariosByBlogTranslationId(0, 0, articleId,new ArrayList<>());
    }

    @PostMapping(UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.COMMENT)
    public ComentariosDTO save(final @Validated @RequestBody ComentariosDTO comentariosDTO) {
        log.info("[ComentariosController][save] comentariosDTO={}", comentariosDTO);
        return comentarioService.save(comentariosDTO);
    }

}
