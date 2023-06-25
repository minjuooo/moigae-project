package com.moigae.application.component;

import com.moigae.application.component.meeting.application.MeetingPaymentCustomService;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final UserRepository userRepository;
    private final MeetingPaymentCustomService meetingPaymentService;

    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id"));
        System.out.println(user);

        model.addAttribute("customUser", customUser);
        model.addAttribute("user", user);
        return "users/mypage";
    }

    @GetMapping("/mypageMoim")
    public String myPageMoim(Model model, @AuthenticationPrincipal CustomUser customUser) {
        String id = customUser.getId();
        MeetingPayment meetingPayment = meetingPaymentService.fetchMeetingPaymentByUserId(id);
        Meeting meeting = meetingPayment.getMeeting();
        Long paidAmount = meetingPayment.getPaidAmount();

        model.addAttribute("customUser", customUser);
        model.addAttribute("meetingPayment", meetingPayment);
        model.addAttribute("meeting", meeting);
        model.addAttribute("paidAmount", paidAmount);
        return "users/mypageMoim";
    }

    @GetMapping("/mypageCart")
    public String myPageCart(Model model, @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("customUser", customUser);
        return "users/mypageCart";
    }

    @GetMapping("/mypageUnJoin")
    public String myPageUnJoin(Model model, @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("customUser", customUser);
        return "users/mypageUnJoin";
    }

    @GetMapping("/mypageFix")
    public String myPageFix(Model model, @AuthenticationPrincipal CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id"));

        model.addAttribute("customUser", customUser);
        model.addAttribute("user", user);
        return "users/mypageFix";
    }
}
