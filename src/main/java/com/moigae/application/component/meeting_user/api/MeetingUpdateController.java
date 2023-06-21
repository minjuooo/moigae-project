package com.moigae.application.component.meeting_user.api;

import com.moigae.application.component.meeting.application.MeetingService;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting_image.service.MeetingImageService;
import com.moigae.application.component.meeting_user.api.request.MeetingUpdateRequest;
import com.moigae.application.component.meeting_user.application.MeetingUserService;
import com.moigae.application.component.meeting_user.util.Converter;
import com.moigae.application.component.user.application.CustomMeetingService;
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
public class MeetingUpdateController {
    private final MeetingImageService meetingImageService;
    private final MeetingUserService meetingUserService;
    private final MeetingService meetingService;
    private final CustomMeetingService hostService;

    private CustomUser customUser;
    private MeetingUpdateRequest updateRequest;

    //수정
    @GetMapping("/meetings/{meetingId}/edit")
    public String getUpdateRequestInfo(@PathVariable("meetingId") String meetingId, Model model) {
        MeetingDto meetingDto = meetingService.meetingFindByUUID(meetingId);
        Meeting meeting = Converter.toMeeting(meetingDto);
        MeetingUpdateRequest updateRequest = Converter.toUpdateRequest(meeting);
        model.addAttribute("meetingUpdateRequest", updateRequest);
        model.addAttribute("customUser", customUser);
        return "host/meeting_edit";
    }


    @PutMapping("/meetings/{meetingId}/edit")
    public String updateMeeting(@PathVariable("meetingId") String meetingId,
                                @ModelAttribute("meetingUpdateRequest") MeetingUpdateRequest updateRequest,
                                @RequestParam("path") MultipartFile path,
                                @AuthenticationPrincipal CustomUser customUser) {
        MeetingDto meetingDto = meetingService.meetingFindByUUID(meetingId);
        hostService.updateMeeting(updateRequest);
//        Meeting meeting = Converter.toMeeting(meetingDto);
        hostService.saveMeeting(meetingDto); // 업데이트된 모임 저장
        return "redirect:/host-center/meetings/{meetingId}/edit";
    }

    //모임삭제
//    @DeleteMapping("/meetings/{meetingId}/delete")
//    public String deleteMeeting(@PathVariable("meetingId") String meetingId) {
//        MeetingDto meetingDto = meetingService.meetingFindByUUID(meetingId);
//        hostService.disableMeeting(updateRequest);
//        return "redirect:/host-center/meetings/main";
//    }

    //모임삭제
    @DeleteMapping("/meetings/{meetingId}/delete")
    public String deleteMeeting(@PathVariable("meetingId") String meetingId) {
        MeetingDto meetingDto = meetingService.meetingFindByUUID(meetingId);
//        meetingDto.setVisible(false); // visible 필드 값을 false로 설정
        hostService.saveMeeting(meetingDto); // 업데이트 메소드로 변경 사항 저장

        return "redirect:/host-center/meetings/main";
    }


}