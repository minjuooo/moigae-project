package com.moigae.application.component.qna.api.service;


import com.moigae.application.component.qna.dto.QuestionWithSymCountDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<QuestionWithSymCountDto> getQuestionsWithSymCount(Pageable pageable, String sort, String searchTerm) {
        String jpql = "SELECT new com.moigae.application.component.qna.dto.QuestionWithSymCountDto(q, " +
                "(SELECT COUNT(s) FROM Sym s WHERE s.question.id = q.id AND s.sym = true), " +
                "(SELECT COUNT(a) FROM Answer a WHERE a.question.id = q.id)) " +
                "FROM Question q " +
                "WHERE q.questionTitle LIKE :searchTerm OR q.questionContent LIKE :searchTerm";


        if ("views".equals(sort)) {
            jpql += " ORDER BY q.viewCount DESC";
        } else {
            jpql += " ORDER BY q.createTime DESC";
        }

        return getQuestionWithSymCountDtos(pageable, jpql, searchTerm);
    }


    private Page<QuestionWithSymCountDto> getQuestionWithSymCountDtos(Pageable pageable, String jpql, String searchTerm) {
        String countJpql = "SELECT COUNT(q) FROM com.moigae.application.component.question.domain.Question q WHERE q.questionTitle LIKE :searchTerm OR q.questionContent LIKE :searchTerm";

        TypedQuery<QuestionWithSymCountDto> query = entityManager.createQuery(jpql, QuestionWithSymCountDto.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        countQuery.setParameter("searchTerm", "%" + searchTerm + "%");

        // 페이지에 해당하는 데이터만 가져옴
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<QuestionWithSymCountDto> content = query.getResultList();
        Long total = countQuery.getSingleResult();

        for (QuestionWithSymCountDto qd : content) {
            Document document = Jsoup.parse(qd.getQuestionContent());
            String parseContent = document.text();
            qd.setQuestionContent(parseContent);
            qd.setRelativeTime(getRelativeTime(qd.getCreateTime()));
        }

        return new PageImpl<>(content, pageable, total);
    }

    public QuestionWithSymCountDto getQuestionWithSymCount(String questionId) {
       /* String jpql = "SELECT new com.moigae.application.component.qna.dto.QuestionWithSymCountDto(q, SUM(CASE WHEN s.sym = true THEN 1 ELSE 0 END), COUNT(a)) " +
                "FROM com.moigae.application.component.question.domain.Question q " +
                "LEFT JOIN com.moigae.application.component.answer.domain.Answer a ON a.question.id = q.id " +
                "LEFT JOIN com.moigae.application.component.qna.domain.Sym s ON s.question.id = q.id " +
                "WHERE q.id = :questionId " +
                "GROUP BY q";*/
        String jpql = "SELECT new com.moigae.application.component.qna.dto.QuestionWithSymCountDto(q, COUNT(CASE WHEN s.sym = true THEN 1 ELSE null END)) " +
                "FROM com.moigae.application.component.question.domain.Question q " +
                "LEFT JOIN com.moigae.application.component.qna.domain.Sym s ON s.question.id = q.id " +
                "WHERE q.id = :questionId " +
                "GROUP BY q";


        TypedQuery<QuestionWithSymCountDto> query = entityManager.createQuery(jpql, QuestionWithSymCountDto.class);
        query.setParameter("questionId", questionId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            // No result found, return null or throw an exception as per your requirement
            return null;
        } catch (NonUniqueResultException e) {
            // More than one result found, throw an exception or handle as per your requirement
            throw new RuntimeException("Non unique result for questionId " + questionId);
        }
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

    public Page<QuestionWithSymCountDto> getQuestionsWithSymCount2(Pageable pageable, String sort, String searchTerm, String currentId) {
        String jpql = "SELECT new com.moigae.application.component.qna.dto.QuestionWithSymCountDto(q, " +
                "(SELECT COUNT(s) FROM Sym s WHERE s.question.id = q.id AND s.sym = true), " +
                "(SELECT COUNT(a) FROM Answer a WHERE a.question.id = q.id)) " +
                "FROM Question q " +
                "WHERE (q.questionTitle LIKE :searchTerm OR q.questionContent LIKE :searchTerm) " +
                "AND q.user.id = :currentId ";
        if ("views".equals(sort)) {
            jpql += " ORDER BY q.viewCount DESC";
        } else {
            jpql += " ORDER BY q.createTime DESC";
        }

        return getQuestionWithSymCountDtos2(pageable, jpql, searchTerm, currentId);
    }

    private Page<QuestionWithSymCountDto> getQuestionWithSymCountDtos2(Pageable pageable, String jpql, String searchTerm, String currentId) {
        String countJpql = "SELECT COUNT(q) FROM com.moigae.application.component.question.domain.Question q " +
                "WHERE (q.questionTitle LIKE :searchTerm OR q.questionContent LIKE :searchTerm) " +
                "AND q.user.id = :currentId";

        TypedQuery<QuestionWithSymCountDto> query = entityManager.createQuery(jpql, QuestionWithSymCountDto.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        query.setParameter("currentId", currentId);
        countQuery.setParameter("searchTerm", "%" + searchTerm + "%");
        countQuery.setParameter("currentId", currentId);

        // 페이지에 해당하는 데이터만 가져옴
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<QuestionWithSymCountDto> content = query.getResultList();
        Long total = countQuery.getSingleResult();

        for (QuestionWithSymCountDto qd : content) {
            Document document = Jsoup.parse(qd.getQuestionContent());
            String parseContent = document.text();
            qd.setQuestionContent(parseContent);
            qd.setRelativeTime(getRelativeTime(qd.getCreateTime()));
        }

        return new PageImpl<>(content, pageable, total);
    }

    public Page<QuestionWithSymCountDto> getQuestionsWithSymCount3(Pageable pageable, String sort, String searchTerm, String currentId) {
        String baseJpql = "SELECT new com.moigae.application.component.qna.dto.QuestionWithSymCountDto(q, " +
                "(SELECT COUNT(s) FROM Sym s WHERE s.question.id = q.id AND s.sym = true), " +
                "(SELECT COUNT(a) FROM Answer a WHERE a.question.id = q.id)) " +
                "FROM Question q " +
                "WHERE q.id IN (SELECT a.question.id FROM Answer a WHERE a.user.id = :currentId)";


        String searchJpql = "";
        if (searchTerm != null && !searchTerm.isEmpty()) {
            searchJpql = " AND (q.questionTitle LIKE :searchTerm OR q.questionContent LIKE :searchTerm)";
        }

        String orderJpql = "";
        if ("views".equals(sort)) {
            orderJpql = " ORDER BY q.viewCount DESC";
        } else {
            orderJpql = " ORDER BY q.createTime DESC";
        }

        return getQuestionWithSymCountDtos3(pageable, baseJpql + searchJpql + orderJpql, searchTerm, currentId);
    }

    private Page<QuestionWithSymCountDto> getQuestionWithSymCountDtos3(Pageable pageable, String jpql, String searchTerm, String currentId) {
        String countJpql = "SELECT COUNT(q) FROM com.moigae.application.component.question.domain.Question q WHERE q.id IN (SELECT a.question.id FROM com.moigae.application.component.answer.domain.Answer a WHERE a.user.id = :currentId)";

        if (searchTerm != null && !searchTerm.isEmpty()) {
            countJpql += " AND (q.questionTitle LIKE :searchTerm OR q.questionContent LIKE :searchTerm)";
        }

        TypedQuery<QuestionWithSymCountDto> query = entityManager.createQuery(jpql, QuestionWithSymCountDto.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            countQuery.setParameter("searchTerm", "%" + searchTerm + "%");
        }

        query.setParameter("currentId", currentId);
        countQuery.setParameter("currentId", currentId);

        // 페이지에 해당하는 데이터만 가져옴
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<QuestionWithSymCountDto> content = query.getResultList();
        Long total = countQuery.getSingleResult();

        for (QuestionWithSymCountDto qd : content) {
            Document document = Jsoup.parse(qd.getQuestionContent());
            String parseContent = document.text();
            qd.setQuestionContent(parseContent);
            qd.setRelativeTime(getRelativeTime(qd.getCreateTime()));
        }

        return new PageImpl<>(content, pageable, total);
    }


}
