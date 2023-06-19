package com.moigae.application.component.qna.domain;

import com.moigae.application.component.question.domain.Question;
import com.moigae.application.component.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sym")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Sym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sym", columnDefinition = "boolean default false")
    private boolean sym;

    public Sym(Question question, User user, boolean sym) {
        this.question = question;
        this.user = user;
        this.sym = sym;
    }
}
