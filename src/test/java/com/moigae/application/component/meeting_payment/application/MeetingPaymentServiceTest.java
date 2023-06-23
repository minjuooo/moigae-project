package com.moigae.application.component.meeting_payment.application;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingAddress;
import com.moigae.application.component.meeting.domain.MeetingContact;
import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.*;
import com.moigae.application.component.meeting.repository.MeetingRepositoryCustom;
import com.moigae.application.component.meeting_image.domain.MeetingImage;
import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import com.moigae.application.component.meeting_payment.domain.enumeration.RefundStatus;
import com.moigae.application.component.meeting_payment.domain.enumeration.TradeStatus;
import com.moigae.application.component.meeting_payment.repository.MeetingPaymentRepository;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.domain.enumeration.Gender;
import com.moigae.application.component.user.domain.enumeration.UserRole;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.repository.UserCustomRepository;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockMvcTest
class MeetingPaymentServiceTest {
    private static final String PHONE = "010";
    private static final String EMAIL = "email";
    private static final int CURRENT_PARTICIPANTS = 0;
    private static final int MAX_PARTICIPANTS = 10;
    private static final String ADDRESS = "주소";
    private static final String DETAIL_ADDRESS = "상세 주소";
    private static final String LOCATION_DESCRIPTION = "위치 정보";
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
    private MeetingPaymentRepository meetingPaymentRepository;
    @Mock
    private MeetingRepositoryCustom meetingRepositoryCustom;
    @Mock
    private UserCustomRepository userCustomRepository;
    @InjectMocks
    private MeetingPaymentService meetingPaymentService;

    CustomUser customUser;
    User user;
    Meeting meeting;
    MeetingPayment meetingPayment;

    @BeforeEach
    void setUp() {
        customUser = new CustomUser();
        customUser.setId("testUserId");
        customUser.setUsername("testUsername");
        customUser.setPassword("testPassword");
        customUser.setName("testName");

        participantRange = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        meetingAddress = MeetingAddress.of(ADDRESS, DETAIL_ADDRESS, LOCATION_DESCRIPTION);
        meetingContact = createMeetingContact();
        meetingImage = createMeetingImage();

        user = createUser();
        meeting = createMeeting();
        meetingPayment = createMeetingPayment();
    }

    @Test
    @DisplayName("유료 결제 - DB 저장")
    public void 유료_결제_DB_저장() {
        //given
        String orderId = "testOrderId";
        Long amount = 100L;
        String meetingId = "testMeetingId";

        //when
        when(userCustomRepository.findCustomUserById(customUser.getId())).thenReturn(user);
        when(meetingRepositoryCustom.findCustomMeetingByPayId(meetingId)).thenReturn(meeting);
        when(meetingPaymentRepository.findByUserAndMeeting(user, meeting)).thenReturn(null);
        when(meetingPaymentRepository.save(any(MeetingPayment.class))).thenReturn(meetingPayment);
        meetingPaymentService.meetingPaymentCreate(orderId, amount, customUser, meetingId);

        //then
        verify(userCustomRepository).findCustomUserById(customUser.getId());
        verify(meetingRepositoryCustom).findCustomMeetingByPayId(meetingId);
        verify(meetingPaymentRepository).findByUserAndMeeting(user, meeting);
        verify(meetingPaymentRepository).save(any(MeetingPayment.class));
    }

    @Test
    @DisplayName("무료 신청 - DB 저장")
    public void 무료_신청_DB_저장() {
        //given
        String orderId = "testOrderId";
        String meetingId = "testMeetingId";

        //when
        when(userCustomRepository.findCustomUserById(customUser.getId())).thenReturn(user);
        when(meetingRepositoryCustom.findCustomMeetingByPayId(meetingId)).thenReturn(meeting);
        when(meetingPaymentRepository.save(any(MeetingPayment.class))).thenReturn(meetingPayment);
        meetingPaymentService.meetingFreeCreate(orderId, customUser, meetingId);

        //then
        verify(userCustomRepository).findCustomUserById(customUser.getId());
        verify(meetingRepositoryCustom).findCustomMeetingByPayId(meetingId);
        verify(meetingPaymentRepository).save(any(MeetingPayment.class));
    }

    @Test
    @DisplayName("isExistingPayment - 중복 결제")
    public void isExistingPayment_ExistingPayment() {
        //given & when
        when(meetingPaymentRepository.findByUserAndMeeting(user, meeting)).thenReturn(meetingPayment);

        //then
        assertThrows(IllegalStateException.class, () -> meetingPaymentService.isExistingPayment(user, meeting));
    }

    @Test
    @DisplayName("isExistingPayment - 중복 결제 아님")
    public void isExistingPayment_NonExistingPayment() {
        //given & when
        when(meetingPaymentRepository.findByUserAndMeeting(user, meeting)).thenReturn(null);

        //then
        assertDoesNotThrow(() -> meetingPaymentService.isExistingPayment(user, meeting));
    }

    @Test
    @DisplayName("checkOrderId - 중복 주문")
    public void checkOrderId_ExistingOrder() {
        //given
        String orderId = "testOrderId";

        //when
        when(meetingPaymentRepository.findByMeetingOrderId(orderId)).thenReturn(meetingPayment);

        //then
        assertTrue(meetingPaymentService.checkOrderId(orderId));
    }

    @Test
    @DisplayName("checkOrderId - 중복 주문 아님")
    public void checkOrderId_NonExistingOrder() {
        //given
        String orderId = "testOrderId";

        //when
        when(meetingPaymentRepository.findByMeetingOrderId(orderId)).thenReturn(null);

        //then
        assertFalse(meetingPaymentService.checkOrderId(orderId));
    }

    private static User createUser() {
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
}