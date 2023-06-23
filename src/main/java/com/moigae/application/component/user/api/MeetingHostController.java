//package com.moigae.application.component.user.api;
//
//import com.moigae.application.component.meeting.domain.ParticipantRange;
//import com.moigae.application.component.meeting.repository.MeetingRepository;
//import com.moigae.application.component.user.application.CustomMeetingService;
//import com.moigae.application.component.user.dto.CustomUser;
//import com.moigae.application.component.user.dto.MeetingPostDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//
//@Controller
//@RequestMapping("/meetingsHost")
//@RequiredArgsConstructor
//public class MeetingHostController {
//
//    private final CustomMeetingService meetingHostService;
//    private final MeetingRepository meetingRepository;
//
//    @GetMapping("/make_meeting")
//    public String createMeeting(
//            Model model,
//            @AuthenticationPrincipal CustomUser customUser,
//            @RequestParam("datetime-local") String startDateTime) {
////            @RequestParam("endDateTime") String endDateTime) {
//
//        MeetingPostDto meetingPostDto = new MeetingPostDto();
//
//        // 서버에서 유저가 입력한 데이터를 LocalDateTime으로 변환하여 설정
//        LocalDateTime recruitmentStartDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
////        LocalDateTime recruitmentEndDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        meetingPostDto.setRecruitmentStartDateTime(recruitmentStartDateTime);
////        meetingPostDto.setRecruitmentEndDateTime(recruitmentEndDateTime);
//
//        if (meetingPostDto.getParticipantRange() == null) {
//            ParticipantRange participantRange = ParticipantRange.of(0, 0);  // 예시로 0으로 설정
//            meetingPostDto.setParticipantRange(participantRange);
//        }
//
//        model.addAttribute("meetingPostDto", meetingPostDto);
//        model.addAttribute("customUser", customUser);
//
//        return "meetings/make_meeting";
//    }
//}
