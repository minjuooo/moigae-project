//package com.moigae.application.component.article.api;
//
//import com.moigae.application.component.article.api.dto.FileUploadDTO;
//import com.moigae.application.component.article.api.service.FileService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.security.Principal;
//
//@RestController
//@RequiredArgsConstructor
//public class FileController {
//
//    private final FileService fileService;
//
//    @PostMapping("/image/upload")
//    public FileUploadDTO imageUpload(
//            @RequestPart MultipartFile upload,
//            Principal principal
//    ) throws IOException {
//
//        return fileService.fileUpload(upload, principal);
//    }
//
//}
