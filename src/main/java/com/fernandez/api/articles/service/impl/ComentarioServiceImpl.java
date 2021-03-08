package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.ComentariosDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Comentarios;
import com.fernandez.api.articles.model.ComentariosUserNotRegistered;
import com.fernandez.api.articles.repository.CommentsRepository;
import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final UserRepository userRepository;

    private final CommentsRepository commentsRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final Messages messages;

    @Override
    public List<ComentariosDTO> findAllComentariosByBlogTranslationId(final long comentarioId, final long level, Long blogsTranslation, final List<ComentariosDTO> comentariosList) {
        List<Comentarios> comentarios = commentsRepository.findAllByParentIdAndArticleId(comentarioId, blogsTranslation);
        if (comentarios != null) {
            for (Comentarios comentario : comentarios) {
                comentariosList.add(mapFromDtoToEntity(comentario, level));
                findAllComentariosByBlogTranslationId(comentario.getId(), level + 1, blogsTranslation, comentariosList);
            }
        }
        return comentariosList;
    }

    @Override
    public ComentariosDTO save(final ComentariosDTO comentariosDTO) {
        Comentarios comentarios = modelMapper.map(comentariosDTO, Comentarios.class);
        if (Objects.nonNull(comentariosDTO.getComentarioUserNotRegistered())) {
            if (Objects.nonNull(comentariosDTO.getComentarioUserNotRegistered().getEmail())) {
                userRepository.findByEmail(comentariosDTO.getComentarioUserNotRegistered().getEmail());
            }
            if (Objects.nonNull(comentariosDTO.getComentarioUserNotRegistered().getUsername())) {
                userRepository.findByUsername(comentariosDTO.getComentarioUserNotRegistered().getUsername());
            }
            ComentariosUserNotRegistered comentariosUserNotRegistered = modelMapper.map(comentariosDTO.getComentarioUserNotRegistered(), ComentariosUserNotRegistered.class);
            comentarios.setComentarioUserNotRegistered(comentariosUserNotRegistered);
        }
        return modelMapper.map(commentsRepository.save(comentarios), ComentariosDTO.class);
    }

    @Override
    public ComentariosDTO findCommentById(Long commentId) {
        return modelMapper.map(commentsRepository.findById(commentId)
                        .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.COMMENT_NOT_FOUND)))
                , ComentariosDTO.class);
    }

    @Override
    public void deleteById(Long commentId) {
        commentsRepository.delete(commentsRepository.findById(commentId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.COMMENT_NOT_FOUND))));
    }

    private ComentariosDTO mapFromDtoToEntity(final Comentarios comentarios,final Long level) {
        ComentariosDTO comentariosDTO = modelMapper.map(comentarios,ComentariosDTO.class);
        comentariosDTO.setLevel(level);
        return comentariosDTO;
    }
}