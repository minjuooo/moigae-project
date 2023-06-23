package com.moigae.application.component.host.controller;

import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import com.moigae.application.component.meeting_payment.repository.MeetingPaymentRepository;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.UserDto;
import com.moigae.application.component.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/host-center")
public class UserNowController {

    private final UserRepository userRepository;
    private final MeetingPaymentRepository meetingPaymentRepository;

    @GetMapping("/meetings/{meetingId}/info/userNow")
    public String getUserInfo(@PathVariable("meetingId") String meetingId, Model model,
                              @AuthenticationPrincipal CustomUser customUser) {
        List<MeetingPayment> meetingPayments = meetingPaymentRepository.findByMeetingId(meetingId);

        List<UserDto> userDtos = meetingPayments.stream()
                .map(payment -> UserDto.of(payment.getUser()))
                .collect(Collectors.toList());

        model.addAttribute("users", userDtos);

        return "host/userNow";
    }

}

//    @GetMapping("/meetings/{meetingId}/info/userNow")
//    public String getUserInfo(@PathVariable("meetingId") Long meetingId, Model model,
//                              @AuthenticationPrincipal CustomUser customUser) {
//
//        List<MeetingPayment> meetingPayments = meetingPaymentRepository.findByMeetingId(meetingId);
//
//        UserDto userDto = UserDto.of(user); // UserDto 클래스로 User 정보 변환
//
//        model.addAttribute("user", userDto);
//        model.addAttribute("meetingPayments", meetingPayments);
//
//        return "host/userNow";
//    }

//    @GetMapping("/meetings/{meetingId}/info/userNow")
////    @{/meetings/{meetingId}/info/userNow(meetingId=${BankAccountDto.id})}
//    public String getUserInfo(Model model, @AuthenticationPrincipal CustomUser customUser) {
//        User user = userRepository.findById(customUser.getId())
//                .orElseThrow(()-> new ResourceNotFoundException("User not found with id"));
////        log.info("getCustomUserboard = {} ", user);
//        UserDto userDto = UserDto.of(user);
//        model.addAttribute("user", userDto); // 모델에 유저 정보를 추가
//        //리스트로 넘겨주면됨
//        return "host/userNow"; // 사용자 정보를 보여주는 HTML 파일의 이름
//    }

