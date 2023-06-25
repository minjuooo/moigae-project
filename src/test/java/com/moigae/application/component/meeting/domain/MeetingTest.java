package com.moigae.application.component.meeting.domain;

import com.moigae.application.component.meeting.domain.enumeraion.*;
import com.moigae.application.component.meeting_image.domain.MeetingImage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MeetingTest {
    private static final int CURRENT_PARTICIPANTS = 0;
    private static final int MAX_PARTICIPANTS = 10;
    private static final String ADDRESS = "주소";
    private static final String DETAIL_ADDRESS = "상세 주소";
    private static final String LOCATION_DESCRIPTION = "위치 정보";
    private static final String PHONE = "010";
    private static final String EMAIL = "email";
    private static final String KAKAO_ID = "kakaoId";
    private static final String LINK = "link";
    private static final String OTHER_LINK = "otherLink";
    private ParticipantRange participantRange;
    private MeetingAddress meetingAddress;
    private MeetingContact meetingContact;
    private MeetingImage meetingImage;
    private MeetingType meetingType = MeetingType.ONLINE;
    private MeetingCategory meetingCategory = MeetingCategory.PARTY;
    private MeetingPrice meetingPrice = MeetingPrice.PAY;
    private PetAllowedStatus petAllowedStatus = PetAllowedStatus.WITH_OUT;
    private MeetingStatus meetingStatus = MeetingStatus.AVAILABLE;

    @BeforeEach
    void setUp() {
        participantRange = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        meetingAddress = MeetingAddress.of(ADDRESS, DETAIL_ADDRESS, LOCATION_DESCRIPTION);
        meetingContact = createMeetingContact();
        meetingImage = createMeetingImage();
    }

    @Test
    @DisplayName("모임 도메인 생성 테스트")
    void 모임_도메인_생성_테스트() {
        //given
        Meeting meeting = new Meeting();
        //when
        meeting = createMeeting();
        //then
        모임_도메인_생성_검증(meeting);
    }

    @Test
    @DisplayName("모임 도메인 디폴트 생성자 테스트")
    void 모임_도메인_디폴트_생성자_테스트() {
        Meeting meeting = new Meeting();
        log.info(meeting.getId());
    }

    @Test
    @DisplayName("모임 도메인 setter 테스트")
    void 모임_도메인_setter_테스트() {
        //given
        Meeting meeting = new Meeting();

        //when
        meeting.setMeetingTitle("New Title");
        meeting.setMeetingType(MeetingType.OFFLINE);
        meeting.setMeetingCategory(MeetingCategory.PARTY);
        meeting.setNickName("New Nickname");
        meeting.setMeetingImage(createMeetingImage());
        meeting.setMeetingDescription("New Description");
        meeting.setRecruitmentStartDateTime(LocalDateTime.now().minusDays(5));
        meeting.setRecruitmentEndDateTime(LocalDateTime.now().plusDays(5));
        meeting.setParticipantRange(ParticipantRange.of(1, 20));
        meeting.setMeetingStartDateTime(LocalDateTime.now().plusDays(10));
        meeting.setMeetingEndDateTime(LocalDateTime.now().plusDays(20));
        meeting.setMeetingAddress(MeetingAddress.of("New Address", "New Detail Address", "New Location Description"));
        meeting.setMeetingPrice(MeetingPrice.FREE);
        meeting.setMeetingAmount(100);
        meeting.setPetAllowedStatus(PetAllowedStatus.WITH_OUT);
        meeting.setMeetingContact(createMeetingContact());
        meeting.setMeetingFreeFormDetails("New Free Form Details");
        meeting.setMeetingStatus(MeetingStatus.AVAILABLE);

        //then
        assertThat(meeting.getMeetingTitle()).isEqualTo("New Title");
        assertThat(meeting.getMeetingType()).isEqualTo(MeetingType.OFFLINE);
        assertThat(meeting.getMeetingCategory()).isEqualTo(MeetingCategory.PARTY);
        assertThat(meeting.getNickName()).isEqualTo("New Nickname");
        assertThat(meeting.getMeetingImage()).isNotNull();
        assertThat(meeting.getMeetingDescription()).isEqualTo("New Description");
        assertThat(meeting.getParticipantRange().getCurrentParticipants()).isEqualTo(1);
        assertThat(meeting.getParticipantRange().getMaxParticipants()).isEqualTo(20);
        assertThat(meeting.getMeetingAddress().getAddress()).isEqualTo("New Address");
        assertThat(meeting.getMeetingPrice()).isEqualTo(MeetingPrice.FREE);
        assertThat(meeting.getMeetingAmount()).isEqualTo(100);
        assertThat(meeting.getPetAllowedStatus()).isEqualTo(PetAllowedStatus.WITH_OUT);
        assertThat(meeting.getMeetingContact()).isNotNull();
        assertThat(meeting.getMeetingFreeFormDetails()).isEqualTo("New Free Form Details");
        assertThat(meeting.getMeetingStatus()).isEqualTo(MeetingStatus.AVAILABLE);
    }

    private static MeetingImage createMeetingImage() {
        return MeetingImage.builder()
                .originName("원본 이름")
                .name("이미지 이름")
                .s3Key("s3-key")
                .path("이미지 경로")
                .build();
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

    private Meeting createMeeting() {
        return Meeting.builder()
                .meetingTitle("타이틀")
                .meetingType(meetingType)
                .meetingCategory(meetingCategory)
                .nickName("닉네임")
                .meetingImage(meetingImage)
                .meetingDescription("모임 소개")
                .participantRange(participantRange)
                .meetingAddress(meetingAddress)
                .meetingPrice(meetingPrice)
                .petAllowedStatus(petAllowedStatus)
                .meetingContact(meetingContact)
                .meetingFreeFormDetails("모임 정보 자유 작성")
                .meetingStatus(meetingStatus)
                .build();
    }

    private void 모임_도메인_생성_검증(Meeting meeting) {
        assertThat(meeting.getMeetingTitle()).isEqualTo("타이틀");
        assertThat(meeting.getMeetingType()).isEqualTo(meetingType);
        assertThat(meeting.getMeetingCategory()).isEqualTo(meetingCategory);
        assertThat(meeting.getNickName()).isEqualTo("닉네임");
        assertThat(meeting.getMeetingDescription()).isEqualTo("모임 소개");
        assertThat(meeting.getParticipantRange()).isEqualTo(participantRange);
        assertThat(meeting.getMeetingAddress()).isEqualTo(meetingAddress);
        assertThat(meeting.getMeetingPrice()).isEqualTo(meetingPrice);
        assertThat(meeting.getPetAllowedStatus()).isEqualTo(petAllowedStatus);
        assertThat(meeting.getMeetingContact()).isEqualTo(meetingContact);
        assertThat(meeting.getMeetingFreeFormDetails()).isEqualTo("모임 정보 자유 작성");
    }
}