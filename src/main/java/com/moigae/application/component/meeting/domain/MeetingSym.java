package com.moigae.application.component.meeting.domain;

import com.moigae.application.component.question.domain.Question;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.core.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "meeting_sym")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class MeetingSym extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sym", columnDefinition = "boolean default false")
    private boolean sym;

    public MeetingSym(Meeting meeting, User user, boolean sym) {
        this.meeting = meeting;
        this.user = user;
        this.sym = sym;
    }
}