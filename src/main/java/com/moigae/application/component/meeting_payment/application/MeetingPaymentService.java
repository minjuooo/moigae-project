package com.moigae.application.component.meeting_payment.application;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.repository.MeetingRepositoryCustom;
import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import com.moigae.application.component.meeting_payment.domain.enumeration.TradeStatus;
import com.moigae.application.component.meeting_payment.repository.MeetingPaymentRepository;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingPaymentService {
    private final MeetingPaymentRepository meetingPaymentRepository;
    private final MeetingRepositoryCustom meetingRepositoryCustom;
    private final UserCustomRepository userCustomRepository;

    //유료 - 결제
    @Transactional
    public void meetingPaymentCreate(String orderId, Long amount, CustomUser customUser, String meetingId) {
        String userId = customUser.getId();
        User user = userCustomRepository.findCustomUserById(userId);
        Meeting meeting = meetingRepositoryCustom.findCustomMeetingByPayId(meetingId);

        isExistingPayment(user, meeting);
        incrementParticipantCount(meeting);
        isMeetingApplyCondition(meeting);

        MeetingPayment meetingPayment = createMeetingPayment(amount, user, meeting, orderId);
        meetingPaymentRepository.save(meetingPayment);
    }

    //무료 - 신청
    @Transactional
    public void meetingFreeCreate(String orderId, CustomUser customUser, String meetingId) {
        String userId = customUser.getId();
        User user = userCustomRepository.findCustomUserById(userId);
        Meeting meeting = meetingRepositoryCustom.findCustomMeetingByPayId(meetingId);

        isExistingPayment(user, meeting);
        incrementParticipantCount(meeting);
        isMeetingApplyCondition(meeting);

        orderId = orderId + "__" + (Math.random() + "").substring(2);
        MeetingPayment meetingPayment = createMeetingPayment(user, meeting, orderId);
        meetingPaymentRepository.save(meetingPayment);
    }

    //유료 - 결제
    public static MeetingPayment createMeetingPayment(Long amount, User user, Meeting meeting, String orderId) {
        return MeetingPayment.builder()
                .user(user)
                .tradeStatus(TradeStatus.valueOf("SUCCESS"))
                .meeting(meeting)
                .paidAmount(amount)
                .meetingOrderId(orderId)
                .build();
    }

    //무료 - 신청
    public static MeetingPayment createMeetingPayment(User user, Meeting meeting, String orderId) {
        return MeetingPayment.builder()
                .user(user)
                .tradeStatus(TradeStatus.valueOf("APPLY"))
                .meeting(meeting)
                .meetingOrderId(orderId)
                .build();
    }

    //모임 (신청, 결제) 전 -> 모임 중복 신청 체크 로직
    public void isExistingPayment(User user, Meeting meeting) {
        MeetingPayment existingPayment = meetingPaymentRepository.findByUserAndMeeting(user, meeting);
        if (existingPayment != null) {
            throw new IllegalStateException("해당 유저는 이미 해당 모임에 신청을 완료했습니다.");
        }
    }

    //유료 모임 - 중복 결제를 방지하기 위한 로직
    public boolean checkOrderId(String orderId) {
        MeetingPayment meetingPayment = meetingPaymentRepository.findByMeetingOrderId(orderId);
        if (meetingPayment == null || meetingPayment.getMeetingOrderId() == null) {
            return false;
        }
        return true;
    }

    //모임 (유료, 무료) - 신청 시, 현재 참여 인원 +1
    public static void incrementParticipantCount(Meeting meeting) {
        meeting.getParticipantRange().addCurrentParticipants();
    }

    //현재 참여 인원 >= 최대 참여 인원 시, 모임 신청 불가 체크
    public static void isMeetingApplyCondition(Meeting meeting) {
        meeting.getParticipantRange().meetingApplyCondition();
    }
}