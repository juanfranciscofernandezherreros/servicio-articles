package com.fernandez.api.articles.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuditDTO {

    private String createdOn;
    private String updatedOn;

}
