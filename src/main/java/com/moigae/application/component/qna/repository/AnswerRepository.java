package com.moigae.application.component.qna.repository;

import com.moigae.application.component.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
    @Query("SELECT a FROM Answer a WHERE a.question.id = :questionId ORDER BY a.createTime ASC")
    List<Answer> findAnswersByQuestionIdSortedByCreatedAtDesc(@Param("questionId") String questionId);
}
