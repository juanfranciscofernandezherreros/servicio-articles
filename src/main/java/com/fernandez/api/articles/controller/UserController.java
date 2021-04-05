package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.service.TagService;
import com.fernandez.api.articles.service.UserService;
import com.fernandez.api.articles.wrapper.ArticleWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlMapping.ROOT, produces = {APPLICATION_JSON_VALUE})
public class UserController { 
	
	private final UserService service;

	@GetMapping (value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.USERS)
	public Page<UserDTO> findAll( @PageableDefault (size = 6) final Pageable pageable) {
		log.info("[UserController][findAll] pageable={}",pageable );
		return service.findAll(pageable);
	}

	@GetMapping (value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.USER)
	public UserDTO findByUsername(@RequestParam (required = true) final String username) {
		log.info("[UserController][findByUsername]");
		return service.findByUsername(username);
	}
}
