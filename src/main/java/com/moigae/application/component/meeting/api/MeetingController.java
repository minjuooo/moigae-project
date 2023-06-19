package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.application.MeetingService;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting_payment.application.MeetingPaymentService;
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
    private final MeetingPaymentService meetingPaymentService;

    @GetMapping
    public String meetings(Model model,
                           @AuthenticationPrincipal CustomUser customUser,
                           @ModelAttribute MeetingCategoryRequest meetingCategoryRequest,
                           @PageableDefault(size = 20) Pageable pageable) {
        Page<MeetingDto> meetingDtoPage = meetingService.Meetings(meetingCategoryRequest, pageable);
        meetingModel(model, customUser, meetingCategoryRequest, meetingDtoPage);
        return "meetings/meeting_list";
    }

    @GetMapping("/{meetingId}")
    public String detailMeeting(Model model,
                                @AuthenticationPrincipal CustomUser customUser,
                                @PathVariable String meetingId) {
        MeetingDto meetingDto = meetingService.meetingFindByUUIDPay(meetingId);
        if (meetingDto.getPrice() > 0) {
            meetingDetailModel(model, customUser, meetingDto);
            return "meetings/meeting_detail_pay";
        }
        meetingDetailModel(model, customUser, meetingDto);
        return "meetings/meeting_detail_free";
    }

    @GetMapping("/{meetingId}/applications")
    public String applicationPayMeeting(Model model,
                                        @AuthenticationPrincipal CustomUser customUser,
                                        @PathVariable String meetingId) {
        if (customUser == null) {
            return "redirect:/users/login";
        }
        UserDto userDto = userService.customUserFindBy(customUser);
        MeetingDto meetingDto = meetingService.meetingFindByUUIDPay(meetingId);
        if (meetingDto.getPrice() > 0) {
            applicationModel(model, customUser, userDto, meetingDto);
            return "meetings/meeting_application_pay";
        }
        applicationModel(model, customUser, userDto, meetingDto);
        return "meetings/meeting_application_free";
    }

    //무료 모임 신청
    @PostMapping("/{meetingId}/applications")
    public String applicationFreeMeeting(Model model,
                                         @AuthenticationPrincipal CustomUser customUser,
                                         @PathVariable String meetingId) {
        if (customUser == null) {
            return "redirect:/users/login";
        }
        meetingPaymentService.meetingFreeCreate(meetingId, customUser, meetingId);
        return "redirect:/meetings/{meetingId}";
    }

    private static void applicationModel(Model model, CustomUser customUser, UserDto userDto, MeetingDto meetingDto) {
        model.addAttribute("meetingDto", meetingDto);
        model.addAttribute("userDto", userDto);
        model.addAttribute("customUser", customUser);
    }

    private static void meetingDetailModel(Model model, CustomUser customUser, MeetingDto meetingDto) {
        model.addAttribute("meetingDto", meetingDto);
        model.addAttribute("customUser", customUser);
    }

    private static void meetingModel(Model model, CustomUser customUser, MeetingCategoryRequest meetingCategoryRequest,
                                     Page<MeetingDto> meetingDtoPage) {
        model.addAttribute("meetingDtoPage", meetingDtoPage);
        model.addAttribute("meetingCategoryDto", meetingCategoryRequest);
        model.addAttribute("customUser", customUser);
    }
}