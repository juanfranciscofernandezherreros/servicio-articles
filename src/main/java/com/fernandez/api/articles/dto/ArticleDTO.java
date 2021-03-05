package com.fernandez.api.articles.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults ( level = AccessLevel.PRIVATE )
@ToString
public class ArticleDTO {

    Long id;

    @NonNull
    String title;

    @NonNull
    String slug;

    @NonNull
    String description;

    @NonNull
    String content;

    @NonNull
    String mainImage;

    @NonNull
    String language;

    UserDTO user;

    @JsonProperty ( access = JsonProperty.Access.READ_ONLY )
    String createdDate;

    Long totalComments;

    AuditDTO auditDTO;

    List < TagDTO > tags = new ArrayList <> ( );

    List < CategoryDTO > categories = new ArrayList <> ( );

}
