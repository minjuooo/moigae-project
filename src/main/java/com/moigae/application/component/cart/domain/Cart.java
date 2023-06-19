package com.moigae.application.component.cart.domain;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    public Cart(User user, Meeting meeting) {
        this.user = user;
        this.meeting = meeting;
    }

    //정적 팩토리 메소드
    public static Cart of(User user, Meeting meeting) {
        return new Cart(user, meeting);
    }
}