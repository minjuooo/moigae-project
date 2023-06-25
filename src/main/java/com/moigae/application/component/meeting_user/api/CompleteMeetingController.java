package com.moigae.application.component.meeting_user.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompleteMeetingController {
    @GetMapping("/completeMeeting")
    public String completeMeeting(Model model) {
        model.addAttribute("message", "모임이 성공적으로 만들어졌어요!");

        return "completeMeeting";
    }
}
