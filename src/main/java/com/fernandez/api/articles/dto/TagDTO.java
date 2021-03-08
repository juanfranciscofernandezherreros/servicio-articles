package com.fernandez.api.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;

    @NonNull
    @NotEmpty
    private String name;

    @NonNull
    @NotEmpty
    private String language;

    @NonNull
    @NotEmpty
    private String slug;
}
