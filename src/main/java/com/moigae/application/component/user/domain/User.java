package com.moigae.application.component.user.domain;

import com.moigae.application.component.answer.domain.Answer;
import com.moigae.application.component.cart.domain.Cart;
import com.moigae.application.component.host.domain.Account;
import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import com.moigae.application.component.meeting_user.domain.MeetingUser;
import com.moigae.application.component.qna.domain.Sym;
import com.moigae.application.component.question.domain.Question;
import com.moigae.application.component.user.domain.enumeration.Gender;
import com.moigae.application.component.user.domain.enumeration.UserRole;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @Column(length = 191)
    @GeneratedValue(generator = "CUID")
    @GenericGenerator(name = "CUID", strategy = "com.moigae.application.core.config.PrimaryGenerator")
    private String id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password", length = 191)
    private String password;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "address")
    @Embedded
    private Address address;

    @Column(name = "account")
    private String account;

    @Column(name = "self_introduction", length = 5000)
    private String hostIntroduction;

    @Column(name = "email")
    private String email;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    //회원 탈퇴 시, false 값일 경우 batch 처리
    @Column(name = "flag", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean flag;

    //회원 탈퇴 시, 회원 탈퇴 시점이 3개월 이상 -> batch 처리
    @Column(name = "deactivateAt", columnDefinition = "DATETIME(3)")
    private LocalDateTime deactivateAt;

    @Builder
    public User(String id, String userName, String password, Gender gender, String phone, LocalDate birth,
                Address address, String account, String hostIntroduction, String email, UserRole userRole,
                boolean flag, LocalDateTime deactivateAt) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.birth = birth;
        this.address = address;
        this.account = account;
        this.hostIntroduction = hostIntroduction;
        this.email = email;
        this.userRole = userRole;
        this.flag = flag;
        this.deactivateAt = deactivateAt;
    }

    //회원 탈퇴
    public User deactivateUser() {
        this.flag = false;
        this.deactivateAt = LocalDateTime.now();  // 비활성화 시점 설정
        return this;
    }
}