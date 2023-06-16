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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> getQuestionsWithSymCount() {
        String jpql = "SELECT q, COUNT(a.sym) FROM Question q " +
                "LEFT JOIN Answer a ON a.question.id = q.id AND a.sym = true " +
                "GROUP BY q";

        return entityManager.createQuery(jpql).getResultList();
    }
    public Page<QuestionWithSymCountDto> getQuestionsWithSymCount(Pageable pageable) {
        String jpql = "SELECT new com.moigae.application.component.qna.dto.QuestionWithSymCountDto(q, COUNT(a.sym), COUNT(a)) " +
                "FROM com.moigae.application.component.qna.domain.Question q " +
                "LEFT JOIN com.moigae.application.component.qna.domain.Answer a ON a.question.id = q.id " +
                "GROUP BY q " +
                "ORDER BY q.createTime DESC";

        List<QuestionWithSymCountDto> results = entityManager.createQuery(jpql, QuestionWithSymCountDto.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        for (QuestionWithSymCountDto qd : results){
            Document document = Jsoup.parse(qd.getQuestionContent());
            String parseContent = document.text();
            qd.setQuestionContent(parseContent);
            qd.setRelativeTime(getRelativeTime(qd.getCreateTime()));
        }
        return new PageImpl<>(results, pageable, results.size());
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
