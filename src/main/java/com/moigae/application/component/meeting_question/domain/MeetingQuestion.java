package com.moigae.application.component.meeting_question.domain;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "meeting_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "meeting_question_title")
    private String meetingQuestionTitle;

    @Column(name = "meeting_question_content")
    private String meetingQuestionContent;

    @Builder
    public MeetingQuestion(Meeting meeting, User user, String meetingQuestionTitle,
                           String meetingQuestionContent) {
        this.meeting = meeting;
        this.user = user;
        this.meetingQuestionTitle = meetingQuestionTitle;
        this.meetingQuestionContent = meetingQuestionContent;
    }
}