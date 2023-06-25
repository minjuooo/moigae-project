package com.moigae.application.component.user.application;


import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting_user.api.request.MeetingUpdateRequest;
import com.moigae.application.component.meeting_user.util.Converter;
import com.moigae.application.component.user.dto.MeetingPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomMeetingService {
    private final MeetingRepository meetingRepository;

    @Transactional
    public void updateMeeting(Meeting meeting) {
        //Database save
        meetingRepository.save(meeting);
    }

    public void insert(MeetingPostDto meetingPostDto) {
        meetingRepository.save(meetingPostDto.toMeeting(meetingPostDto));
    }

    public MeetingDto updateMeeting(MeetingUpdateRequest updateRequest) {
        Meeting meeting = Converter.fromUpdateRequestToMeeting(updateRequest);
        Meeting newMeeting = meetingRepository.save(meeting);
        return Converter.toMeetingDto(newMeeting);
    }

//    public MeetingDto disableMeeting(MeetingUpdateRequest updateRequest) {
//        Meeting meeting = Converter.fromUpdateRequestToMeeting(updateRequest);
//        Meeting newMeeting = meetingRepository.save(meeting);
//        return Converter.toMeetingDto(newMeeting);
//    }

//    public void saveMeeting(MeetingDto meetingDto) {
//        // MeetingDto를 Meeting 객체로 변환
//        Meeting meeting = Converter.toMeeting(meetingDto);
//
//        meetingRepository.save(meeting);
//    }
//
//    public void updateMeeting(MeetingDto meetingDto) {
//        Meeting meeting = Converter.toMeeting(meetingDto);
//        meetingRepository.save(meeting);
//    }
}