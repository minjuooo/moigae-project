package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.application.MeetingService;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.user.dto.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {
    private final MeetingService meetingService;

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
                                @PathVariable Long meetingId) {
        MeetingDto meetingDto = meetingService.meetingDto(meetingId);
        model.addAttribute("meetingDto", meetingDto);
        return "meetings/meeting_detail";
    }

    @GetMapping("/{meetingId}/applications")
    public String applicationMeeting(Model model,
                                     @PathVariable Long meetingId) {
        MeetingDto meetingDto = meetingService.meetingDto(meetingId);
        model.addAttribute("meetingDto", meetingDto);
        return "meetings/meeting_application";
    }

//    @PostMapping("/{meetingId}/applications")
//    public String applicationMeeting(Model model,
//                                     @PathVariable Long meetingId) {
//        return "redirect:/{meetingId}";
//    }
}