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
        MeetingPayment meetingPayment = createMeetingPayment(amount, user, meeting, orderId);

        meetingPaymentRepository.save(meetingPayment);
    }

    //무료 - 신청
    @Transactional
    public void meetingFreeCreate(String orderId, CustomUser customUser, String meetingId) {
        String userId = customUser.getId();
        User user = userCustomRepository.findCustomUserById(userId);
        Meeting meeting = meetingRepositoryCustom.findCustomMeetingByPayId(meetingId);
        orderId = orderId + "__" + (Math.random() + "").substring(2);
        MeetingPayment meetingPayment = createMeetingPayment(user, meeting, orderId);

        meetingPaymentRepository.save(meetingPayment);
    }

    //유료 - 결제
    private static MeetingPayment createMeetingPayment(Long amount, User user, Meeting meeting, String orderId) {
        return MeetingPayment.builder()
                .user(user)
                .tradeStatus(TradeStatus.valueOf("SUCCESS"))
                .meeting(meeting)
                .paidAmount(amount)
                .meetingOrderId(orderId)
                .build();
    }

    //무료 - 신청
    private static MeetingPayment createMeetingPayment(User user, Meeting meeting, String orderId) {
        return MeetingPayment.builder()
                .user(user)
                .tradeStatus(TradeStatus.valueOf("APPLY"))
                .meeting(meeting)
                .meetingOrderId(orderId)
                .build();
    }
}