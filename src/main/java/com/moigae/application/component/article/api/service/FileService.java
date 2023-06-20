package com.moigae.application.component.article.api.service;

import com.moigae.application.component.article.api.dto.FileUploadDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileUploadDTO fileUpload(MultipartFile upload, Principal principal) throws IOException {
        File uploadFile = convert(upload).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환실패."));

        return upload(uploadFile, "static");
    }

    private FileUploadDTO upload(File uploadFile, String dir) {
        String sourceName = uploadFile.getName();
        String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();

        String fileName = dir + "/" + LocalDateTime.now().toString().concat(".").concat(sourceExt);
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        return FileUploadDTO.builder()
                .uploaded(true)
                .fileName(fileName)
                .url(uploadImageUrl)
                .build();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public String putS3(File uploadFile, String fileName) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(fileName)
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .build(),
                    RequestBody.fromFile(uploadFile));
        } catch (Exception e) {
            log.error("이미지 s3 업로드 실패");
            log.error(e.getMessage());
            removeNewFile(uploadFile);
            throw new RuntimeException();
        }

        return s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build()).toString();
    }

    public Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }

            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public String getUrl(String html) {
        String regex = "src=\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}