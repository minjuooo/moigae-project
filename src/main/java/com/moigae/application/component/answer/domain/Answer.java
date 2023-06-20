package com.moigae.application.component.answer.domain;

import com.moigae.application.component.question.domain.Question;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.core.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Answer extends BaseEntity {
    @Id
    @Column(length = 191)
    @GeneratedValue(generator = "CUID")
    @GenericGenerator(name = "CUID", strategy = "com.moigae.application.core.config.PrimaryGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private User user;

    @Column(name = "answer_content", length = 14000)
    private String answerContent;

    @Column(name = "sym", columnDefinition = "boolean default false")
    private boolean sym;

    public Answer(Question question, User user, String answerContent, boolean sym) {
        this.question = question;
        this.user = user;
        this.answerContent = answerContent;
        this.sym = sym;
    }

    //    @Builder
//    public Answer(MeetingQuestion meetingQuestion, User user, String meetingAnswerComment) {
//        this.meetingQuestion = meetingQuestion;
//        this.user = user;
//        this.meetingAnswerComment = meetingAnswerComment;
//    }
}