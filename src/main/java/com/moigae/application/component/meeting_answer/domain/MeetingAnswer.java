package com.moigae.application.component.meeting_answer.domain;

import com.moigae.application.component.meeting_question.domain.MeetingQuestion;
import com.moigae.application.component.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "meeting_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_question_id")
    private MeetingQuestion meetingQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "meeting_answer_comment")
    private String meetingAnswerComment;

    @Builder
    public MeetingAnswer(MeetingQuestion meetingQuestion, User user, String meetingAnswerComment) {
        this.meetingQuestion = meetingQuestion;
        this.user = user;
        this.meetingAnswerComment = meetingAnswerComment;
    }
}