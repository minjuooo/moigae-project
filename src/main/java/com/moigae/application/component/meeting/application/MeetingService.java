package com.moigae.application.component.meeting.application;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.QMeeting;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;

    @Transactional(readOnly = true)
    public Page<MeetingDto> searchOffMeeting(String searchTitle, Pageable pageable) {
        QMeeting meeting = QMeeting.meeting;
        BooleanBuilder builder = new BooleanBuilder();

        isSearchTitleNotNull(searchTitle, meeting, builder);
        Page<Meeting> meetingPage = meetingRepository.findAll(builder, pageable);
        return meetingPage.map(MeetingDto::toMeetingDto);
    }

    private static void isSearchTitleNotNull(String searchTitle, QMeeting meeting, BooleanBuilder builder) {
        if (searchTitle != null) {
            builder.and(meeting.meetingTitle.containsIgnoreCase(searchTitle));
        }
    }
}