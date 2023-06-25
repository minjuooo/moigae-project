package com.moigae.application.component.meeting.application;

import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting_payment.repository.MeetingPaymentRepository;
import com.moigae.application.component.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainUserService {
    private final MeetingPaymentRepository meetingPaymentRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

//    @Transactional
//    public MeetingPaymentDto getMeetingPayment(Long id) {
//        MeetingPayment meetingPayment = meetingPaymentRepository.findById(id).orElse(null);
//
//        if (meetingPayment != null) {
//            User user = userRepository.findById(meetingPayment.getUser().getId()).orElse(null);
//            Meeting meeting = meetingRepository.findById(meetingPayment.getMeeting().getId()).orElse(null);
//
//            return new MeetingPaymentDto(user, meeting, meetingPayment);
//        }
//
//        return null;
//    }
}
