package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.application.MeetingService;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingSym;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting.repository.MeetingSymRepository;
import com.moigae.application.component.meeting_payment.application.MeetingPaymentService;
import com.moigae.application.component.user.application.UserService;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.UserDto;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.ZoneId;
import java.util.Date;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {
    private final MeetingService meetingService;
    private final UserService userService;
    private final MeetingPaymentService meetingPaymentService;
    private final MeetingSymRepository meetingSymRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    @GetMapping
    public String meetings(Model model,
                           @AuthenticationPrincipal CustomUser customUser,
                           @ModelAttribute MeetingCategoryRequest meetingCategoryRequest,
                           @PageableDefault(size = 12, sort = "createTime") Pageable pageable) {
        Page<MeetingDto> meetingDtoPage = meetingService.Meetings(meetingCategoryRequest, pageable);
        log.info(String.valueOf("📌📌📌📌📌 데이터 페이지 개수 : " + meetingDtoPage.getTotalPages()));
        model.addAttribute("meetingDtoPage", meetingDtoPage);
        model.addAttribute("meetingCategoryDto", meetingCategoryRequest);
        model.addAttribute("customUser", customUser);
        return "meetings/meeting_list";
    }

    @GetMapping("/{meetingId}")
    public String detailMeeting(Model model,
                                @AuthenticationPrincipal CustomUser customUser,
                                @PathVariable String meetingId) {
        MeetingDto meetingDto = meetingService.meetingFindByUUIDPay(meetingId);

        boolean sym = false;
        if(customUser !=null){
            MeetingSym meetingSym = meetingSymRepository.findByUserIdAndMeetingId(customUser.getId(), meetingDto.getId());
            if(meetingSym == null || !meetingSym.isSym()){
                sym = false;
            }else{
                sym = true;
            }
        }
      
        if (meetingDto.getPrice() > 0) {
            model.addAttribute("meetingDto", meetingDto);
            model.addAttribute("customUser", customUser);
            model.addAttribute("sym", sym);
            return "meetings/meeting_detail_pay";
        }
        model.addAttribute("meetingDto", meetingDto);
        model.addAttribute("customUser", customUser);
        model.addAttribute("sym", sym);
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
            model.addAttribute("meetingDto", meetingDto);
            model.addAttribute("userDto", userDto);
            model.addAttribute("customUser", customUser);
            return "meetings/meeting_application_pay";
        }
        model.addAttribute("meetingDto", meetingDto);
        model.addAttribute("userDto", userDto);
        model.addAttribute("customUser", customUser);
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

    @PostMapping("/symUp")
    @ResponseBody
    public Map<String, Boolean> findPassWord(
            @RequestBody Map<String, String> req) {
        String userId = req.get("userId");
        String meetingId = req.get("meetingId");
        System.out.println(userId);
        System.out.println(meetingId);
        MeetingSym meetingSym = meetingSymRepository.findByUserIdAndMeetingId(userId, meetingId);

        if(meetingSym == null){
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
            Meeting meeting = meetingRepository.findById(meetingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id " + meetingId));
            meetingSym = new MeetingSym(meeting, user, true);
        }else{
            meetingSym.setSym(!meetingSym.isSym());
        }

        meetingSymRepository.save(meetingSym);
        Map<String, Boolean> response = new HashMap<>();
        response.put("statusSym", meetingSym.isSym());

        return response;
    }
}