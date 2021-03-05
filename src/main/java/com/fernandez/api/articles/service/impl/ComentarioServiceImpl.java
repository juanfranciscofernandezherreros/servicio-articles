package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.dto.ComentariosDTO;
import com.fernandez.api.articles.model.Comentarios;
import com.fernandez.api.articles.model.ComentariosUserNotRegistered;
import com.fernandez.api.articles.model.User;
import com.fernandez.api.articles.repository.CommentsRepository;
import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    private ModelMapper modelMapper = new ModelMapper();

    private User foundEmail;

    private User foundUsername;

    @Override
    public List<ComentariosDTO> findAllComentariosByBlogTranslationId(long comentarioId, long level, Long articleId, List<ComentariosDTO> comentariosList) {
        log.info("[ComentarioServiceImpl][findAllComentariosByBlogTranslationId] comentarioId={} level={} articleId={} comentariosList={}", 
                comentarioId , level , articleId , comentariosList);
        List<Comentarios> comentarios = commentsRepository.findAllByParentIdAndArticleId(comentarioId, articleId);
        if (comentarios != null) {
            for (Comentarios comentario : comentarios) {
                if (comentario.getComentarioUserNotRegistered() == null) {
                    comentariosList.add(mapFromDtoToEntity(comentario, level));
                } else {
                    comentariosList.add(mapFromDtoToEntity(comentario, level));
                }
                findAllComentariosByBlogTranslationId(comentario.getId(), level + 1, articleId, comentariosList);
            }
        }
        return comentariosList;
    }

    @Override
    public ComentariosDTO save(ComentariosDTO comentariosDTO) {
        log.info("[ComentarioServiceImpl][save] comentariosDTO={}" , comentariosDTO);
        Comentarios comentarios = modelMapper.map(comentariosDTO, Comentarios.class);
        if (Objects.nonNull(comentariosDTO.getComentarioUserNotRegistered())) {
            if (Objects.nonNull(comentariosDTO.getComentarioUserNotRegistered().getEmail())) {
                foundEmail = userRepository.findByEmail(comentariosDTO.getComentarioUserNotRegistered().getEmail());
            }
            if (Objects.nonNull(comentariosDTO.getComentarioUserNotRegistered().getUsername())) {
                foundUsername = userRepository.findByUsername(comentariosDTO.getComentarioUserNotRegistered().getUsername());
            }
            ComentariosUserNotRegistered comentariosUserNotRegistered = modelMapper.map(comentariosDTO.getComentarioUserNotRegistered(), ComentariosUserNotRegistered.class);
            comentarios.setComentarioUserNotRegistered(comentariosUserNotRegistered);
        }
        return modelMapper.map(commentsRepository.save(comentarios), ComentariosDTO.class);
    }

    private ComentariosDTO mapFromDtoToEntity(Comentarios comentarios,Long level) {
        ComentariosDTO comentariosDTO = modelMapper.map(comentarios,ComentariosDTO.class);
        comentariosDTO.setLevel(level);
        return comentariosDTO;
    }
}