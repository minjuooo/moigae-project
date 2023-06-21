package com.moigae.application.component.meeting_user.api;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting_image.service.MeetingImageService;
import com.moigae.application.component.meeting_user.application.MeetingUserService;
import com.moigae.application.component.meeting_user.domain.MeetingUser;
import com.moigae.application.component.meeting_user.repositroy.MeetingUserRepository;
import com.moigae.application.component.user.dto.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/host-center")
public class HostCenterController {
    private final MeetingImageService meetingImageService;
    private final MeetingUserService meetingUserService;
    private final MeetingUserRepository meetingUserRepository;

    @GetMapping("/meetings/main")
    public String hostMeeting(Model model, @AuthenticationPrincipal CustomUser customUser) {
        List<MeetingUser> meetingUsers = meetingUserRepository.findByHostId(customUser.getId());
        //map 으로 짠거 list로 넘겨서 사진이랑제목만
        Set<Meeting> meetings = meetingUsers.stream()
                .filter(Objects::nonNull)
                .filter(x -> Objects.nonNull(x.getHostId()))
                .filter(x -> customUser.getId().equals(x.getHostId()))
                .map(MeetingUser::getMeeting)
                .collect(Collectors.toSet());
        model.addAttribute("meetings", meetings);
        model.addAttribute("customUser", customUser);
        return "host/hostCenterMain";
    }
}