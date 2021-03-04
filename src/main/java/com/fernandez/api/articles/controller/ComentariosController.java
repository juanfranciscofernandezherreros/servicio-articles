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

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.COMMENTS)
    public List<ComentariosDTO> findAllCommentsFromArticle(@RequestParam(required = true) final Long articleId) {
        log.info("[ComentariosController][findAllCommentsFromArticle] comentariosDTO={}", articleId);
        List<ComentariosDTO> tmpList = new ArrayList<ComentariosDTO>();
        return comentarioService.findAllComentariosByBlogTranslationId(0, 0, articleId,tmpList);
    }

    @PostMapping(value = UrlMapping.PROTECTED + UrlMapping.COMMENTS)
    public ComentariosDTO save(@Validated @RequestBody ComentariosDTO comentariosDTO) {
        log.info("[ComentariosController][save] comentariosDTO={}", comentariosDTO);
        return comentarioService.save(comentariosDTO);
    }

}
