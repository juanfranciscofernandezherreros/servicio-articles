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
@ToString
public class ArticleDTO {

    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String slug;

    @NonNull
    private String description;

    @NonNull
    private String content;

    @NonNull
    private String mainImage;

    @NonNull
    private String language;

    private UserDTO user;

    @JsonProperty ( access = JsonProperty.Access.READ_ONLY )
    private String createdDate;

    private Long totalComments;

    private AuditDTO auditDTO;

    private List < TagDTO > tags = new ArrayList <> ( );

    private List < CategoryDTO > categories = new ArrayList <> ( );

}
