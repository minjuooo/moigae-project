package com.moigae.application.component.meeting.repository;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.dto.MeetingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MeetingRepositoryCustom {
    Page<MeetingDto> findMeetingsByCondition(MeetingCategoryRequest meetingCategoryRequest, Pageable pageable);

    //uuid 대응
    MeetingDto findCustomMeetingById(String meetingId);

    //uuid 대응
    Meeting findCustomMeetingByPayId(String meetingId);
}