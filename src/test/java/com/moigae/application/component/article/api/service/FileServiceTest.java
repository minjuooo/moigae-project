package com.moigae.application.component.article.api.service;

import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@MockMvcTest
class FileServiceTest {
    @Mock
    private MultipartFile multipartFile;
    @Mock
    private Principal principal;
    @InjectMocks
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("MultipartFile -> File 전환 실패")
    public void 아티클_파일_업로드_실패_테스트() throws IOException {
        //when
        when(multipartFile.getOriginalFilename()).thenReturn("testFile.txt");
        when(multipartFile.getBytes()).thenReturn("testFileContent".getBytes());

        //then
        assertThatThrownBy(() -> fileService.fileUpload(multipartFile, principal))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("MultipartFile null 값으로 인한, 파일 -> Convert 실패")
    void Convert_실패_테스트() {
        //given
        MultipartFile nullMultipartFile = null;

        //when & then
        assertThatThrownBy(() -> fileService.convert(nullMultipartFile))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("파일 S3 업로드 및 URL 실패 테스트")
    public void 파일_S3_업로드_및_URL_실패_테스트() {
        //given
        File mockFile = new File("testFile.txt");
        String mockFileName = "static/testFile.txt";
        String expectedUrl = "https://example.com/" + mockFileName;

        //when & then
        assertThatThrownBy(() -> fileService.putS3(mockFile, expectedUrl))
                .isInstanceOf(RuntimeException.class);
    }
}