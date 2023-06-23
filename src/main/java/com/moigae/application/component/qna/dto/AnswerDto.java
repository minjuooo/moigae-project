package com.moigae.application.component.qna.dto;

import com.moigae.application.component.qna.repository.QuestionRepository;
import com.moigae.application.component.question.domain.Question;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

