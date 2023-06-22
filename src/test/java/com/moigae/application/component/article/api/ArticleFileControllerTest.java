package com.moigae.application.component.article.api;

import com.moigae.application.component.article.api.dto.FileUploadDTO;
import com.moigae.application.component.article.api.service.FileService;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class ArticleFileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @MockBean
    private Principal principal;

    private MockMultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        multipartFile = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        when(principal.getName()).thenReturn("username");
    }

    @Test
    @DisplayName("아티클 - 이미지 업로드 컨트롤러 테스트")
    void imageUploadTest() throws Exception {
        //given
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setFileName("hello.txt");

        //when
        when(fileService.fileUpload(any(), eq(principal))).thenReturn(fileUploadDTO);

        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/imageArticle/upload")
                        .file("upload", multipartFile.getBytes())
                        .principal(principal))
                .andExpect(status().isOk());
    }
}