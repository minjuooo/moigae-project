package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.application.MeetingService;
import com.moigae.application.component.meeting.dto.MeetingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {
    private final MeetingService meetingService;

    @GetMapping
    public String onMeeting(Model model,
                            @RequestParam(required = false, defaultValue = "") String titleSearch,
                            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        Page<MeetingDto> meetingDtoPage = meetingService.searchOffMeeting(titleSearch, pageable);
        model.addAttribute("meetingDtoPage", meetingDtoPage);
        return "meetings/meeting_list";
    }
}