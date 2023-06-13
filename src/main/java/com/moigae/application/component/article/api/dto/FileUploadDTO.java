package com.moigae.application.component.article.api.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDTO {
    private boolean uploaded;
    private String fileName;
    private String url;
}
