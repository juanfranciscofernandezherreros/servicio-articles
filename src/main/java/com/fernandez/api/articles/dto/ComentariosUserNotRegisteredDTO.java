package com.fernandez.api.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComentariosUserNotRegisteredDTO {

    private Long id;
    private String email;
    private String username;

}
