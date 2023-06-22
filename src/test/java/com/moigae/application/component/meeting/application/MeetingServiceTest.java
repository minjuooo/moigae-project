package com.moigae.application.component.meeting.application;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingAddress;
import com.moigae.application.component.meeting.domain.MeetingContact;
import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.*;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting.repository.MeetingRepositoryCustom;
import com.moigae.application.component.meeting_image.domain.MeetingImage;
import com.moigae.application.core.annotation.MockMvcTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@MockMvcTest
@Slf4j
class MeetingServiceTest {
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

    @Mock
    private MeetingRepository meetingRepository;
    @Mock
    private MeetingRepositoryCustom meetingRepositoryCustom;
    @InjectMocks
    private MeetingService meetingService;

    @BeforeEach
    void setUp() {
        participantRange = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        meetingAddress = MeetingAddress.of(ADDRESS, DETAIL_ADDRESS, LOCATION_DESCRIPTION);
        meetingContact = createMeetingContact();
        meetingImage = createMeetingImage();
    }

    @Test
    @DisplayName("중복 동적 쿼리, 페이징 - 모임 조회")
    public void 중복_동적_쿼리_페이징_모임_조회() {
        //given
        MeetingCategoryRequest meetingCategoryRequest = new MeetingCategoryRequest();
        Pageable pageable = PageRequest.of(0, 20);
        MeetingDto meetingDto = new MeetingDto();
        Page<MeetingDto> pageMeetingDto = new PageImpl<>(Arrays.asList(meetingDto));

        //when
        when(meetingRepositoryCustom.findMeetingsByCondition(meetingCategoryRequest, pageable)).thenReturn(pageMeetingDto);
        Page<MeetingDto> result = meetingService.Meetings(meetingCategoryRequest, pageable);

        //then
        assertThat(result.getContent().get(0)).isEqualTo(meetingDto);
        verify(meetingRepositoryCustom, times(1)).findMeetingsByCondition(meetingCategoryRequest, pageable);
    }

    @Test
    @DisplayName("미팅 아이디 조회 - UUID")
    public void 미팅_아이디_조회_UUID() {
        //given
        String meetingId = "meetingId";
        MeetingDto meetingDto = new MeetingDto();

        //when
        when(meetingRepositoryCustom.findCustomMeetingById(meetingId)).thenReturn(meetingDto);
        MeetingDto result = meetingService.meetingFindByUUID(meetingId);

        //then
        assertThat(result).isEqualTo(meetingDto);
        verify(meetingRepositoryCustom, times(1)).findCustomMeetingById(meetingId);
    }

    @Test
    @DisplayName("UUID로 모임 조회 및 결제 테스트 - meeting_amount가 null or 0")
    void meetingFindByUUIDPayTestAmountNullOrZero() {
        //given
        String meetingId = "meetingIdFindByPay";
        Meeting meeting = createMeetingAmountZero(meetingId);
        meetingRepository.save(meeting);

        //when
        when(meetingRepositoryCustom.findCustomMeetingByPayId(meetingId)).thenReturn(meeting);
        MeetingDto result = meetingService.meetingFindByUUIDPay(meetingId);

        //then
        log.info(String.valueOf(result));
        verify(meetingRepositoryCustom, times(1)).findCustomMeetingByPayId(meetingId);
        assertEquals(0, result.getPrice());
    }

    @Test
    @DisplayName("UUID로 모임 조회 및 결제 테스트 - meeting_amount가 null x 그리고 not 0")
    void meetingFindByUUIDPayTestAmountNotNullAndNotZero() {
        //given
        String meetingId = "meetingIdFindByPay";
        Meeting meeting = createMeeting(meetingId);
        meetingRepository.save(meeting);

        //when
        when(meetingRepositoryCustom.findCustomMeetingByPayId(meetingId)).thenReturn(meeting);
        MeetingDto result = meetingService.meetingFindByUUIDPay(meetingId);

        //then
        log.info(String.valueOf(result));
        verify(meetingRepositoryCustom, times(1)).findCustomMeetingByPayId(meetingId);
        assertEquals(100, result.getPrice());
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

    private Meeting createMeeting(String meetingId) {
        return Meeting.builder()
                .id(meetingId)
                .meetingTitle("타이틀")
                .meetingType(meetingType)
                .meetingCategory(meetingCategory)
                .nickName("닉네임")
                .meetingImage(meetingImage)
                .meetingAmount(100)
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

    private Meeting createMeetingAmountZero(String meetingId) {
        return Meeting.builder()
                .id(meetingId)
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
}