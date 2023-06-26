package com.moigae.application.component.meeting_payment.repository;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import com.moigae.application.component.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingPaymentRepository extends JpaRepository<MeetingPayment, Long> {
    MeetingPayment findByUserAndMeeting(User user, Meeting meeting);

    MeetingPayment findByMeetingOrderId(String orderId);

    MeetingPayment findByMeetingId(Long meetingId);

    List<MeetingPayment> findByMeetingId(String meetingId);

    List<MeetingPayment> findByUserId(String userId);

//    Optional<MeetingPayment> findById(Long id);
}