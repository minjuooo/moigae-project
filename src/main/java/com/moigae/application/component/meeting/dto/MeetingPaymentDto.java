package com.moigae.application.component.meeting.dto;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import com.moigae.application.component.user.domain.User;
import lombok.Data;

@Data
public class MeetingPaymentDto {
    private User user;
    private Meeting meeting;
    private MeetingPayment meetingPayment;

//    public MeetingPaymentDto(User user, Meeting meeting, MeetingPayment meetingPayment) {
//        this.user = user;
//        this.meeting = meeting;
//        this.meetingPayment = meetingPayment;
//    }

    public MeetingPaymentDto(User user, Meeting meeting, MeetingPayment meetingPayment) {
        if (user == null || meeting == null || meetingPayment == null) {
            throw new IllegalArgumentException("User, Meeting, or MeetingPayment cannot be null");
        }

        this.user = user;
        this.meeting = meeting;
        this.meetingPayment = meetingPayment;
    }

}
