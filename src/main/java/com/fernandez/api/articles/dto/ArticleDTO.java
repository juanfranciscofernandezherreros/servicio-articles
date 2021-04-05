package com.fernandez.api.articles.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ArticleDTO {

    Long id;

    @NonNull
    @NotEmpty
    String title;

    @NonNull
    @NotEmpty
    String slug;

    @NonNull
    @NotEmpty
    String description;

    @NonNull
    @NotEmpty
    String content;

    @NonNull
    @NotEmpty
    String mainImage;

    @NonNull
    @NotEmpty
    String language;

    @NonNull
    @NotEmpty
    String username;
    
    @Valid
    UserDTO user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String createdDate;

    @Size(max=3)
    List<TagDTO> tags = new ArrayList<>();
    
    @Size(min=1 , max=3)
    List<CategoryDTO> categories;

    List<ComentariosDTO> comentarios = new ArrayList<>();

    Long totalComments;

    AuditDTO auditDTO;

}
