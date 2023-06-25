package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.application.MainUserService;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import com.moigae.application.component.meeting_payment.repository.MeetingPaymentRepository;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class MainUserController {
    private final MainUserService mainUserService;
    private final MeetingPaymentRepository meetingPaymentRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

    @Transactional
    @GetMapping("/meeting-payment/{id}")
    public String getMeetingPayment(@PathVariable("id") Long id, Model model) {
        MeetingPayment meetingPayment = meetingPaymentRepository.findById(id).orElse(null);

        if (meetingPayment != null) {
            User user = userRepository.findById(meetingPayment.getUser().getId()).orElse(null);
            Meeting meeting = meetingRepository.findById(meetingPayment.getMeeting().getId()).orElse(null);

            model.addAttribute("user", user);
            model.addAttribute("meeting", meeting);
            model.addAttribute("meetingPayment", meetingPayment);
        }

        return "users/mypageMoim";
    }
}


//public class MainUserController {
//    private MainUserService mainUserService;
//    private MeetingService meetingService;
//    private MeetingPaymentRepository meetingPaymentRepository;
//
//    @Transactional
//    @GetMapping("/meeting-payment/{id}")
//    public String getMeetingPayment(@PathVariable("id") Long id, Model model) {
//        MeetingPayment meetingPayment = meetingPaymentRepository.findById(id).orElse(null);
//        model.addAttribute("meetingPayment", meetingPayment);
//        return "users/mypageMoim";
//    }

//    @GetMapping("/meeting-payment/{id}")
//    public String getMeetingPayment(@PathVariable("id") String id, Model model) {
//        MeetingPayment meetingPayment = mainUserService.getMeetingPaymentById(id);
//        model.addAttribute("meetingPayment", meetingPayment);
//        return "redirect:/host-center/meetings/main";
//    }

//    @GetMapping("/meeting-payment")
//    public String getMeetingPayment(Model model) {
//        // meeting과 paidAmount가 설정된 MeetingPayment 객체가 있다고 가정합니다.
//        MeetingPayment meetingPayment = getMeetingPaymentFromDatabase(); // 여기에는 직접 구현한 로직을 사용하세요.
//
//        // meeting과 paidAmount를 뷰로 전달합니다.
//        model.addAttribute("meeting", meetingPayment.getMeeting());
//        model.addAttribute("paidAmount", meetingPayment.getPaidAmount());
//
//        return "meetingPayment";
//    }


