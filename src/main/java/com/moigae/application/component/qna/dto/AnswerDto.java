package com.moigae.application.component.qna.dto;

import com.moigae.application.component.qna.domain.Question;
import com.moigae.application.component.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
public class AnswerDto {
    private String id;
    private String questionId;
    private String email;
    private String answerContent;
    private LocalDateTime createTime;
    public AnswerDto(String id, String questionId, String email, String answerContent, LocalDateTime createTime) {
        this.id = id;
        this.questionId = questionId;
        this.email = email;
        this.answerContent = answerContent;
        this.createTime = createTime;
    }
}

