package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.repository.TagsRepository;
import com.fernandez.api.articles.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagsServiceImpl implements TagService {

    private final TagsRepository tagsRepository;

    private final Messages messages;

    private final ModelMapper modelMapper = new ModelMapper ( );

    @Override
    public List < TagDTO > tagDTOList ( final ArticleDTO articleDTO ) {
        log.info ( "[TagsServiceImpl][tagDTOList] articleDTO={}" , articleDTO );
        return articleDTO.getTags ( )
                .stream ( )
                .map ( tag -> findTagByNameAndLanguage ( tag , articleDTO.getLanguage ( ) ) )
                .collect ( Collectors.toList ( ) );
    }

    @Override
    public Tag findTagById ( final Long tagsId ) {
        log.info ( "[TagsServiceImpl][findTagById] tagsId={}" , tagsId );
        return tagsRepository.findById ( tagsId )
                .orElseThrow ( ( ) -> new ArticlesLogicException ( HttpStatus.NOT_FOUND , messages.get ( PropertiesConstant.TAG_NOT_FOUND ) ) );
    }

    @Override
    public TagDTO findTagDtoById ( final Long tagId ) {
        log.info ( "[TagsServiceImpl][findTagDtoById] tagId={}" , tagId );
        return modelMapper.map ( tagsRepository.findById ( tagId ) , TagDTO.class );
    }

    @Override
    public TagDTO save ( final TagDTO tagDTO ) {
        log.info ( "[TagsServiceImpl][save] tagDTO={}" , tagDTO );
        final Tag tag = modelMapper.map ( tagDTO , Tag.class );
        return modelMapper.map ( tagsRepository.save ( tag ) , TagDTO.class );
    }

    @Override
    public void deleteById ( final Long tagId ) {
        log.info ( "[TagsServiceImpl][deleteById] tagId={}" , tagId );
        tagsRepository.delete ( tagsRepository.findById ( tagId )
                .orElseThrow ( ( ) -> new ArticlesLogicException ( HttpStatus.NOT_FOUND , messages.get ( PropertiesConstant.CATEGORY_NOT_FOUND ) ) ) );
    }

    @Override
    public Page < TagDTO > findAll ( final String acceptLanguage , final Pageable pageable ) {
        log.info ( "[TagsServiceImpl][findAll] acceptLanguage={} pageable={}" , acceptLanguage , pageable );
        return tagsRepository.findAllByLanguage ( acceptLanguage , pageable )
                .map ( this :: mapFromEntityToDto );
    }

    @Override
    public Page < TagDTO > findAllTagsRandom ( final String acceptLanguage , final Pageable pageable ) {
        final List < Tag > list = tagsRepository.findAllByLanguage ( acceptLanguage );
        Collections.shuffle ( list , new Random ( System.nanoTime ( ) ) );
        return convertList2Page ( list , pageable );
    }

    private TagDTO mapFromEntityToDto ( final Tag tag ) {
        return modelMapper.map ( tag , TagDTO.class );
    }

    private TagDTO findTagByNameAndLanguage ( final TagDTO tagDTO , final String language ) {
        final ModelMapper modelMapper = new ModelMapper ( );
        final Tag tag = tagsRepository.findByNameAndLanguage ( tagDTO.getName ( ) , language );
        if ( Objects.nonNull ( tag ) ) {
            modelMapper.map ( tag , TagDTO.class );
        } else {
            modelMapper.map ( tagsRepository.save ( modelMapper.map ( tagDTO , Tag.class ) ) , TagDTO.class );
        }
        return tagDTO;
    }

    private Page convertList2Page ( final List list , final Pageable pageable ) {
        final int startIndex = ( int ) pageable.getOffset ( );
        final int endIndex = ( int ) ( ( pageable.getOffset ( ) + pageable.getPageSize ( ) ) > list.size ( ) ?
                list.size ( )
                : pageable.getOffset ( ) + pageable.getPageSize ( ) );
        final List subList = list.subList ( startIndex , endIndex );
        return new PageImpl ( subList , pageable , list.size ( ) );
    }
}
