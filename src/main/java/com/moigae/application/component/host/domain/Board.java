package com.moigae.application.component.host.domain;

import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@NoArgsConstructor
@Getter
public class Board {
    @Id
    @Column(length = 191)
    @GeneratedValue(generator = "CUID")
    @GenericGenerator(name = "CUID", strategy = "com.moigae.application.core.config.PrimaryGenerator")
    private String id;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "meeting_title")
    private String meetingTitle;
    @Column(name = "bank_name")
    private MeetingPrice price;
    @Column(name = "participant_range")
    private ParticipantRange participantRange;
    @Column(name = "meeting_status")
    private MeetingStatus meetingStatus;
//    @Column(name="meetingImage")
//    private String meetingImage;

    @Builder
    public Board(String id, LocalDateTime date, String meetingTitle, MeetingPrice price, ParticipantRange participantRange, MeetingStatus meetingStatus) {
        this.id = id;
        this.date = date;
        this.meetingTitle = meetingTitle;
        this.price = price;
        this.participantRange = participantRange;
        this.meetingStatus = meetingStatus;
    }
}