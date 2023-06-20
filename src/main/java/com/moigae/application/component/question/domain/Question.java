package com.moigae.application.component.question.domain;

import com.moigae.application.component.user.domain.User;
import com.moigae.application.core.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Question extends BaseEntity {
    @Id
    @Column(length = 191)
    @GeneratedValue(generator = "CUID")
    @GenericGenerator(name = "CUID", strategy = "com.moigae.application.core.config.PrimaryGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "question_title")
    private String questionTitle;

    @Column(name = "question_content", length = 14000)
    private String questionContent;

    @Column(name = "view_Count")
    private Long viewCount;

    public Question(User user, String questionTitle, String questionContent, long viewCount) {
        this.user = user;
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
        this.viewCount = viewCount;
    }

    public String getUserId() {
        return this.user.getId();  // assuming User entity has a getId() method
    }
//    @Builder
//    public Question(Meeting meeting, User user, String meetingQuestionTitle,
//                    String meetingQuestionContent) {
//        this.meeting = meeting;
//        this.user = user;
//        this.meetingQuestionTitle = meetingQuestionTitle;
//        this.meetingQuestionContent = meetingQuestionContent;
//    }
}