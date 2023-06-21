package com.moigae.application.component.user.api;

import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.user.application.CustomMeetingService;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.MeetingPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
@RequestMapping("/meetingsHost")
@RequiredArgsConstructor
public class MeetingHostController {

    private final CustomMeetingService meetingHostService;
    private final MeetingRepository meetingRepository;

    @GetMapping("/make_meeting")
    public String createMeeting(
            Model model,
            @AuthenticationPrincipal CustomUser customUser,
            @RequestParam("datetime-local") String startDateTime) {
//            @RequestParam("endDateTime") String endDateTime) {

        MeetingPostDto meetingPostDto = new MeetingPostDto();

        // 서버에서 유저가 입력한 데이터를 LocalDateTime으로 변환하여 설정
        LocalDateTime recruitmentStartDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        LocalDateTime recruitmentEndDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        meetingPostDto.setRecruitmentStartDateTime(recruitmentStartDateTime);
//        meetingPostDto.setRecruitmentEndDateTime(recruitmentEndDateTime);

        if (meetingPostDto.getParticipantRange() == null) {
            ParticipantRange participantRange = ParticipantRange.of(0, 0);  // 예시로 0으로 설정
            meetingPostDto.setParticipantRange(participantRange);
        }

        model.addAttribute("meetingPostDto", meetingPostDto);
        model.addAttribute("customUser", customUser);

        return "meetings/make_meeting";
    }
}


//    @GetMapping("/make_meeting")
//    public String createMeeting(
//            Model model,
//            @AuthenticationPrincipal CustomUser customUser) {
//
//        MeetingPostDto meetingPostDto = new MeetingPostDto();
//
//        meetingPostDto.setRecruitmentStartDateTime(LocalDateTime.now().minusDays(5));
//        meetingPostDto.setRecruitmentEndDateTime(LocalDateTime.now());
////        meetingPostDto.
//        if (meetingPostDto.getParticipantRange() == null) {
//            ParticipantRange participantRange = ParticipantRange.of(0, 0);  // 예시로 0으로 설정
//            meetingPostDto.setParticipantRange(participantRange);
//        }
//        model.addAttribute("meetingPostDto", meetingPostDto);
//        model.addAttribute("customUser", customUser);
//        return "meetings/make_meeting";
//    }
//}

//    @PostMapping("/make_meeting")
//    public String createMeetingProcess(
//            @ModelAttribute MeetingPostDto meetingPostDto) {
//        // MeetingPostDto에서 필요한 값들을 추출하여 Meeting 객체 생성
//        Meeting meeting = new Meeting();
//        meeting.setRecruitmentStartDateTime(meetingPostDto.getRecruitmentStartDateTime());
//        meeting.setRecruitmentEndDateTime(meetingPostDto.getRecruitmentEndDateTime());
//        // 나머지 필드들도 필요에 따라 설정
//
//        // Meeting 객체를 DB에 저장
//        meetingRepository.save(meeting);
//
//        return "redirect:/";
//    }
//}


//    @PostMapping("/make_meeting")
//    public String createMeetingProcess(
//            @ModelAttribute MeetingPostDto meetingPostDto){
//        ModelMapper modelMapper = new ModelMapper();
//        Meeting meeting = Meeting.builder()
//                .recruitmentStartDateTime(meetingPostDto.getRecruitmentStartDateTime())
//                .recruitmentEndDateTime(meetingPostDto.getRecruitmentEndDateTime())
//                .participantRange(meetingPostDto.getParticipantRange())
//                .meetingStartDateTime(meetingPostDto.getMeetingStartDateTime())
//                .meetingEndDateTime(meetingPostDto.getMeetingEndDateTime())
//                .meetingAddress(meetingPostDto.getMeetingAddress())
//                .petAllowedStatus(meetingPostDto.getPetAllowedStatus())
//                .build();
//        meetingRepository.save(meeting);
//        return "redirect:/";
//    }
//}


//    @PostMapping("/update")
//    public String updateMeeting(@PathVariable("id") Long id,
//                                @ModelAttribute("meetingPostDto") MeetingPostDto meetingPostDto) {
//        meetingHostService.updateMeeting(id, meetingPostDto);
//        return "redirect:/meetings/" + id;
//    }

//    @DeleteMapping("/delete")
//    public String deleteMeeting(@PathVariable Long id) {
//        Meeting meeting = meetingHostService.getMeeting(id);
//        if (meeting == null) {
//            return "<h1>Meeting not found</h1>"; // HTML content indicating meeting not found
//        }
//        meetingHostService.deleteMeeting(id);
//        return "<h1>Meeting deleted successfully</h1>"; // HTML content indicating meeting deletion success
//    }

//    @DeleteMapping("delete")
//    public ResponseEntity<Void> deleteMeeting(@PathVariable String id) {
//        Meeting meeting =hostService.getMeeting(id);
//        if(meeting == null) {
//            return ResponseEntity.notFound().build();
//        }
//        HostService.deleteMeeting(id);
//        return ResponseEntity.noContent().build();
//    }
//}