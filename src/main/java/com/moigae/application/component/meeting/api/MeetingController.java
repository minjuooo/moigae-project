package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.api.request.ApplicationMeetingRequest;
import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.application.MeetingService;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.user.application.UserService;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {
    private final MeetingService meetingService;
    private final UserService userService;

    @GetMapping
    public String meetings(Model model,
                           @AuthenticationPrincipal CustomUser customUser,
                           @ModelAttribute MeetingCategoryRequest meetingCategoryRequest,
                           @PageableDefault(size = 20) Pageable pageable) {
        Page<MeetingDto> meetingDtoPage = meetingService.Meetings(meetingCategoryRequest, pageable);
        model.addAttribute("meetingDtoPage", meetingDtoPage);
        model.addAttribute("meetingCategoryDto", meetingCategoryRequest);
        model.addAttribute("customUser", customUser);
        return "meetings/meeting_list";
    }

    @GetMapping("/{meetingId}")
    public String detailMeeting(Model model,
                                @AuthenticationPrincipal CustomUser customUser,
                                @PathVariable String meetingId) {
        MeetingDto meetingDto = meetingService.meetingFindByUUID(meetingId);
        model.addAttribute("meetingDto", meetingDto);
        model.addAttribute("customUser", customUser);
        return "meetings/meeting_detail";
    }

    @GetMapping("/{meetingId}/applications")
    public String applicationMeeting(Model model,
                                     @AuthenticationPrincipal CustomUser customUser,
                                     @PathVariable String meetingId) {
        if (customUser == null) {
            return "redirect:/users/login";
        }
        UserDto userDto = userService.customUserFindBy(customUser);
        MeetingDto meetingDto = meetingService.meetingFindByUUID(meetingId);
        model.addAttribute("meetingDto", meetingDto);
        model.addAttribute("userDto", userDto);
        model.addAttribute("customUser", customUser);
        return "meetings/meeting_application";
    }

    @PostMapping("/{meetingId}/applications")
    public String applicationMeeting(Model model,
                                     @AuthenticationPrincipal CustomUser customUser,
                                     @PathVariable String meetingId,
                                     @ModelAttribute ApplicationMeetingRequest applicationMeetingRequest) {
        if (customUser == null) {
            return "redirect:/users/login";
        }
        model.addAttribute("customUser", customUser);
        return "redirect:/{meetingId}";
    }
}