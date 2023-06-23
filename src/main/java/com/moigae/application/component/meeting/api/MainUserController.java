package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.application.MainUserService;
import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class MainUserController {
    private MainUserService mainUserService;

    @GetMapping("/meeting-payment/{id}")
    public String getMeetingPayment(@PathVariable("id") String id, Model model) {
        MeetingPayment meetingPayment = mainUserService.getMeetingPaymentById(id);
        model.addAttribute("meetingPayment", meetingPayment);
        return "redirect:/host-center/meetings/main";
    }


}
