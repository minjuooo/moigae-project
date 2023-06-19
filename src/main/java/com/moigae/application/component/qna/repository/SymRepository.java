package com.moigae.application.component.qna.repository;

import com.moigae.application.component.qna.domain.Sym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymRepository extends JpaRepository<Sym, Long> {
    Sym findByUserIdAndQuestionId(String userId, String questionId);

    List<Sym> findByQuestionIdAndSymTrue(String questionId);
}
