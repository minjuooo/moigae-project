package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meetings")
public class CountController {
    private final MeetingRepository meetingRepository;

    @Autowired
    public CountController(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @GetMapping("/count")
    public String getMeetingCount() {
        long count = meetingRepository.count();
        return count + "개";
    }

}
