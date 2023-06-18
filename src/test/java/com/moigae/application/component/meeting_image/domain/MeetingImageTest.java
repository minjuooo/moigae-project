package com.moigae.application.component.meeting_image.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MeetingImageTest {
    private static final String UPDATE_INIT = "update";

    @Test
    @DisplayName("모임 이미지 도메인 생성 테스트")
    void 모임_이미지_도메인_생성_테스트() {
        //given & when
        MeetingImage meetingImage = createMeetingImage();
        //then
        모임_이미지_도메인_검증(meetingImage);
    }

    @Test
    @DisplayName("모임 이미지 도메인 업데이트 테스트")
    void 모임_이미지_도메인_업데이트_테스트() {
        //given
        MeetingImage meetingImage = createMeetingImage();
        //when
        meetingImage.update(UPDATE_INIT, UPDATE_INIT, UPDATE_INIT, UPDATE_INIT);
        //then
        모임_이미지_도메인_업데이트_검증(meetingImage);
    }

    private static MeetingImage createMeetingImage() {
        return MeetingImage.builder()
                .originName("원본 이름")
                .name("이미지 이름")
                .s3Key("s3-key")
                .path("이미지 경로")
                .build();
    }

    private static void 모임_이미지_도메인_검증(MeetingImage meetingImage) {
        assertThat(meetingImage.getOriginName()).isEqualTo("원본 이름");
        assertThat(meetingImage.getName()).isEqualTo("이미지 이름");
        assertThat(meetingImage.getS3Key()).isEqualTo("s3-key");
        assertThat(meetingImage.getPath()).isEqualTo("이미지 경로");
    }

    private static void 모임_이미지_도메인_업데이트_검증(MeetingImage meetingImage) {
        assertThat(meetingImage.getOriginName()).isEqualTo(UPDATE_INIT);
        assertThat(meetingImage.getName()).isEqualTo(UPDATE_INIT);
        assertThat(meetingImage.getS3Key()).isEqualTo(UPDATE_INIT);
        assertThat(meetingImage.getPath()).isEqualTo(UPDATE_INIT);
    }
}