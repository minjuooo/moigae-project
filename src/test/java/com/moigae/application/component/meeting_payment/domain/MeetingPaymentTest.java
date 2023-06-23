package com.moigae.application.component.meeting_payment.domain;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingAddress;
import com.moigae.application.component.meeting.domain.MeetingContact;
import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.*;
import com.moigae.application.component.meeting_image.domain.MeetingImage;
import com.moigae.application.component.meeting_payment.domain.enumeration.RefundStatus;
import com.moigae.application.component.meeting_payment.domain.enumeration.TradeStatus;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.domain.enumeration.Gender;
import com.moigae.application.component.user.domain.enumeration.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingPaymentTest {
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
    private User user;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        participantRange = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        meetingAddress = MeetingAddress.of(ADDRESS, DETAIL_ADDRESS, LOCATION_DESCRIPTION);
        meetingContact = createMeetingContact();
        meetingImage = createMeetingImage();
        meeting = createMeeting();
        user = createUser();
    }

    @Test
    @DisplayName("모임 결제 객체 생성 테스트")
    void 모임_결제_객체_생성_테스트() {
        //given & when
        MeetingPayment meetingPayment = createMeetingPayment();
        //then
        모임_결제_객체_검증(meetingPayment);
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

    private User createUser() {
        return User.builder()
                .userName("홍정완")
                .password("패스워드")
                .gender(Gender.MAN)
                .phone(PHONE)
                .account("계좌")
                .hostIntroduction("호스트 자기소개")
                .email(EMAIL)
                .userRole(UserRole.USER)
                .flag(true)
                .deactivateAt(null)
                .build();
    }

    private MeetingPayment createMeetingPayment() {
        return MeetingPayment.builder()
                .user(user)
                .meeting(meeting)
                .tradeStatus(TradeStatus.SUCCESS)
                .meetingOrderId("주문번호")
                .paidAmount(1000L)
                .calculateAmount(1000L)
                .refundStatus(RefundStatus.NONE)
                .build();
    }

    private void 모임_결제_객체_검증(MeetingPayment meetingPayment) {
        assertThat(meetingPayment.getUser()).isEqualTo(user);
        assertThat(meetingPayment.getMeeting()).isEqualTo(meeting);
        assertThat(meetingPayment.getTradeStatus()).isEqualTo(TradeStatus.SUCCESS);
        assertThat(meetingPayment.getMeetingOrderId()).isEqualTo("주문번호");
        assertThat(meetingPayment.getPaidAmount()).isEqualTo(1000L);
        assertThat(meetingPayment.getCalculateAmount()).isEqualTo(1000L);
        assertThat(meetingPayment.getRefundStatus()).isEqualTo(RefundStatus.NONE);
        assertThat(meetingPayment.getId()).isNull();
    }
}