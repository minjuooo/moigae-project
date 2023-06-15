package com.moigae.application.component.meeting.repository;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.dto.MeetingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MeetingRepositoryCustom {
    Page<MeetingDto> findMeetingsByCondition(MeetingCategoryRequest meetingCategoryRequest, Pageable pageable);

    MeetingDto findCustomMeetingById(String meetingId);
}