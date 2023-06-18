package com.moigae.application.component.qna.api.service;


import com.moigae.application.component.qna.dto.QuestionWithSymCountDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<QuestionWithSymCountDto> getQuestionsWithSymCount(Pageable pageable, String sort) {
        String jpql = "SELECT new com.moigae.application.component.qna.dto.QuestionWithSymCountDto(q, COUNT(a.sym), COUNT(a)) " +
                "FROM com.moigae.application.component.qna.domain.Question q " +
                "LEFT JOIN com.moigae.application.component.qna.domain.Answer a ON a.question.id = q.id " +
                "GROUP BY q";

        if ("views".equals(sort)) {
            jpql += " ORDER BY q.viewCount DESC";
        } else {
            jpql += " ORDER BY q.createTime DESC";
        }

        return getQuestionWithSymCountDtos(pageable, jpql);
    }

    private Page<QuestionWithSymCountDto> getQuestionWithSymCountDtos(Pageable pageable, String jpql) {
        // 전체 레코드 수를 세는 쿼리
        String countJpql = "SELECT COUNT(q) FROM com.moigae.application.component.qna.domain.Question q";

        // 각 Question에 대해 답변 수와 'sym'의 수를 계산하는 쿼리
        String dataJpql = jpql;

        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        long total = countQuery.getSingleResult();

        TypedQuery<QuestionWithSymCountDto> dataQuery = entityManager.createQuery(dataJpql, QuestionWithSymCountDto.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());

        List<QuestionWithSymCountDto> results = dataQuery.getResultList();

        for (QuestionWithSymCountDto qd : results) {
            Document document = Jsoup.parse(qd.getQuestionContent());
            String parseContent = document.text();
            qd.setQuestionContent(parseContent);
            qd.setRelativeTime(getRelativeTime(qd.getCreateTime()));
        }

        return new PageImpl<>(results, pageable, total);
    }

    public String getRelativeTime(LocalDateTime pastTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(pastTime, now);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + "일 전";
        } else if (hours > 0) {
            return hours + "시간 전";
        } else if (minutes > 0) {
            return minutes + "분 전";
        } else {
            return seconds + "초 전";
        }
    }

}
