package com.moigae.application.component.qna.dto;

import com.moigae.application.component.question.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWithSymCountDto {
    private String id;
    private String userId;
    private String questionTitle;
    private String questionContent;
    private Long viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long sym;
    private Long totalAnswersCount;
    private String relativeTime;

    public QuestionWithSymCountDto(Question question, long symCount, long totalAnswersCount) {
        this.id = question.getId();
        this.userId = question.getUserId();
        this.questionTitle = question.getQuestionTitle();
        this.questionContent = question.getQuestionContent();
        this.viewCount = question.getViewCount();
        this.createTime = question.getCreateTime();
        this.updateTime = question.getUpdateTime();
        this.sym = symCount;
        this.totalAnswersCount = totalAnswersCount;
    }

    public QuestionWithSymCountDto(Question question, long symCount) {
        this.id = question.getId();
        this.userId = question.getUserId();
        this.questionTitle = question.getQuestionTitle();
        this.questionContent = question.getQuestionContent();
        this.viewCount = question.getViewCount();
        this.createTime = question.getCreateTime();
        this.updateTime = question.getUpdateTime();
        this.sym = symCount;
    }
}

