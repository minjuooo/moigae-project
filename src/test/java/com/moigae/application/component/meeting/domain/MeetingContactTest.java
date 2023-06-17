package com.moigae.application.component.meeting.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingContactTest {
    private MeetingContact meetingContact = new MeetingContact();
    private static final String PHONE = "010";
    private static final String EMAIL = "email";
    private static final String KAKAO_ID = "kakaoId";
    private static final String LINK = "link";
    private static final String OTHER_LINK = "otherLink";

    @Test
    @DisplayName("모임 연락 객체 생성 테스트")
    void 모임_연락_객체_생성_테스트() {
        //given & when
        meetingContact = createMeetingContact();
        //then
        모임_연락_객체_검증();
    }

    @Test
    @DisplayName("모임 연락 업데이트 테스트")
    void 모임_연락_업데이트_테스트() {
        //given
        meetingContact = createMeetingContact();
        //when
        meetingContact.updateContact("업데이트", "업데이트", "업데이트", "업데이트", "업데이트");
        //then
        모임_연락_업데이트_검증();
    }

    private static MeetingContact createMeetingContact() {
        return MeetingContact.builder()
                .phone(PHONE)
                .email(EMAIL)
                .kakaoId(KAKAO_ID)
                .link(LINK)
                .otherLink(OTHER_LINK)
                .build();
    }

    private void 모임_연락_객체_검증() {
        assertThat(meetingContact.getPhone()).isEqualTo("010");
        assertThat(meetingContact.getEmail()).isEqualTo("email");
        assertThat(meetingContact.getKakaoId()).isEqualTo("kakaoId");
        assertThat(meetingContact.getLink()).isEqualTo("link");
        assertThat(meetingContact.getOtherLink()).isEqualTo("otherLink");
    }

    private void 모임_연락_업데이트_검증() {
        assertThat(meetingContact.getPhone()).isEqualTo("업데이트");
        assertThat(meetingContact.getEmail()).isEqualTo("업데이트");
        assertThat(meetingContact.getKakaoId()).isEqualTo("업데이트");
        assertThat(meetingContact.getLink()).isEqualTo("업데이트");
        assertThat(meetingContact.getOtherLink()).isEqualTo("업데이트");
    }
}