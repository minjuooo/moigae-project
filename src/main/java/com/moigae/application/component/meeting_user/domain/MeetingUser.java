package com.moigae.application.component.meeting_user.domain;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "meeting_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(name = "host_id")
    private String hostId;

    @Builder
    public MeetingUser(Long id, User user, Meeting meeting, String hostId) {
        this.id = id;
        this.user = user;
        this.meeting = meeting;
        this.hostId = hostId;
    }

    //정적 팩토리 메소드
    public static MeetingUser of(Long id, User user, Meeting meeting, String hostId) {
        return MeetingUser.builder()
                .id(id)
                .user(user)
                .meeting(meeting)
                .hostId(hostId)
                .build();
    }
}