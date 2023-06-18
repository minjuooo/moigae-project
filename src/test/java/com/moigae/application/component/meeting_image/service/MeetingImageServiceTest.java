package com.moigae.application.component.meeting_image.service;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting_image.domain.MeetingImage;
import com.moigae.application.component.meeting_image.repository.MeetingImageRepository;
import com.moigae.application.core.util.s3.S3Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class MeetingImageServiceTest {
    private static final String FILE_NAME = "filename.jpg";
    private static final String S3_KEY = "s3Key";
    private static final String IMAGE_URI = "http://s3.amazonaws.com/bucket/" + FILE_NAME;

    @Autowired
    private MeetingImageService meetingImageService;

    @Autowired
    private MeetingImageRepository meetingImageRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @MockBean
    private S3Utils s3Utils;

    private Meeting meeting;

    private MockMultipartFile imageFile;

    @BeforeEach
    void setUp() {
        meeting = new Meeting();
        imageFile = new MockMultipartFile(
                "imageFile", "origFilename.jpg", "image/jpg", "test-image".getBytes()
        );
    }

    @Test
    @DisplayName("미팅 이미지 생성 & 삭제 테스트")
    @Transactional
    void 미팅_이미지_생성_삭제_테스트() {
        //given
        meetingRepository.save(meeting);

        //when
        when(s3Utils.createFilename(imageFile.getOriginalFilename())).thenReturn(FILE_NAME);
        when(s3Utils.putS3(imageFile, FILE_NAME, MeetingImageService.S3_ACCOUNT_DIR_NAME)).thenReturn(IMAGE_URI);
        when(s3Utils.createS3Key(FILE_NAME, MeetingImageService.S3_ACCOUNT_DIR_NAME)).thenReturn(S3_KEY);

        meetingImageService.create(imageFile, meeting);
        MeetingImage meetingImage = meetingImageRepository.findAll().get(0);

        //then
        모임_이미지_생성_검증(meeting, imageFile, FILE_NAME, S3_KEY, IMAGE_URI, meetingImage);

        //when
        meetingImageService.delete(meeting);

        //then
        verify(s3Utils, times(1)).deleteFromS3(meeting.getMeetingImage());
    }

    private static void 모임_이미지_생성_검증(Meeting meeting, MockMultipartFile imageFile, String filename, String s3Key,
                                     String imageUrl, MeetingImage meetingImage) {
        assertThat(meetingImage.getName()).isEqualTo(filename);
        assertThat(meetingImage.getOriginName()).isEqualTo(imageFile.getOriginalFilename());
        assertThat(meetingImage.getPath()).isEqualTo(imageUrl);
        assertThat(meetingImage.getS3Key()).isEqualTo(s3Key);
        assertThat(meeting.getMeetingImage()).isNotNull();
    }
}