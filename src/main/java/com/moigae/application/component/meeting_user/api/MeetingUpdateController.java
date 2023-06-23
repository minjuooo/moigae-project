package com.moigae.application.component.meeting_user.api;

import com.moigae.application.component.meeting.application.MeetingService;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingStatus;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting.repository.MeetingRepository;
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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/host-center")
public class MeetingUpdateController {
    private final MeetingImageService meetingImageService;
    private final MeetingUserService meetingUserService;
    private final MeetingService meetingService;
    private final CustomMeetingService hostService;
    private final MeetingRepository meetingRepository;

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
    @Transactional
    public String updateMeeting(@PathVariable("meetingId") String meetingId,
                                @ModelAttribute("meetingUpdateRequest") MeetingUpdateRequest updateRequest,
                                @RequestParam("path") MultipartFile path,
                                @AuthenticationPrincipal CustomUser customUser) {
        MeetingDto meetingDto = meetingService.meetingFindByUUID(meetingId);
        hostService.updateMeeting(updateRequest);
        return "redirect:/host-center/meetings/{meetingId}/edit";
    }

    //모임삭제
    @GetMapping("/meetings/{meetingId}/cancel")
    @Transactional
    public String cancelMeeting(@PathVariable("meetingId") String meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).get();
        log.info("===========Meeting = {} ", meeting);
        // 현재 날짜와 모집 종료 날짜를 비교하여 모집 기간인지 확인
        LocalDate currentDate = LocalDate.now();
        LocalDateTime recruitmentEndDateTime = meeting.getRecruitmentEndDateTime();

        if (currentDate.isBefore(recruitmentEndDateTime.toLocalDate())) {
            // 모집 기간 내에만 취소가 가능하므로 모임 상태를 "CANCELLED"로 설정
            meeting.setMeetingStatus(MeetingStatus.CANCELLED);
            log.info("MeetingStatus = {} {} ", meeting.getMeetingStatus());

            return "redirect:/host-center/board";
        } else {
            // 모집 기간이 지났으므로 취소할 수 없음
            // 다른 처리 로직을 구현하거나 오류 메시지를 반환할 수 있습니다.
            return "redirect:/host-center/meetings/main";
        }
    }
}