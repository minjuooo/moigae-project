package com.moigae.application.component.meeting.domain;

import com.moigae.application.component.meeting.domain.enumeraion.MeetingCategory;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meeting_category")
    @Enumerated(EnumType.STRING)
    private MeetingCategory meetingCategory;

    @Column(name = "description")
    private String description;

    @Column(name = "endDateTime")
    private LocalDateTime endDateTime;

    @Column(name = "startDateTime")
    private LocalDateTime startDateTime;

    @Column(name = "meetingPlace")
    private String meetingPlace;

    @Column(name = "meetingStatus")
    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    @Column(name = "maximumParticipant")
    private Integer maximumParticipant;

    @Column(name = "meeting_title")
    private String meetingTitle;

    @Column(name = "meetingPrice")
    private String meetingPrice;

    @Builder
    public Meeting(MeetingCategory meetingCategory, String description, LocalDateTime endDateTime,
                   LocalDateTime startDateTime, String meetingPlace, MeetingStatus meetingStatus, Integer maximumParticipant, String meetingTitle, String meetingPrice) {
        this.meetingCategory = meetingCategory;
        this.description = description;
        this.endDateTime = endDateTime;
        this.startDateTime = startDateTime;
        this.meetingPlace = meetingPlace;
        this.meetingStatus = meetingStatus;
        this.maximumParticipant = maximumParticipant;
        this.meetingTitle = meetingTitle;
        this.meetingPrice = meetingPrice;
    }
}