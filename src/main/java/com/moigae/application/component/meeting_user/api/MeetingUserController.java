package com.moigae.application.component.meeting_user.api;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting_image.service.MeetingImageService;
import com.moigae.application.component.meeting_user.api.request.MeetingCreateRequest;
import com.moigae.application.component.meeting_user.application.MeetingUserService;
import com.moigae.application.component.user.dto.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/host-center")
public class MeetingUserController {
    private final MeetingImageService meetingImageService;
    private final MeetingUserService meetingUserService;

    @GetMapping("/meetings/create")
    public String createMeeting(Model model) {
        model.addAttribute("meetingCreateRequest", new MeetingCreateRequest());
        return "meetings/meeting_create";
    }

    @PostMapping("/meetings/create")
    public String createMeeting(Model model,
                                @ModelAttribute("meetingCreateRequest") MeetingCreateRequest meetingCreateRequest,
                                @RequestParam("path") MultipartFile path,
                                @AuthenticationPrincipal CustomUser customUser) {
        Meeting meeting = meetingUserService.meetingUserCreate(customUser, meetingCreateRequest);
        meetingImageService.create(path, meeting);
        return "meetings/meeting_create";
    }
}