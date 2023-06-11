package com.moigae.application.component.user.domain;

import com.moigae.application.component.user.domain.enumeration.Gender;
import com.moigae.application.component.user.domain.enumeration.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    @Column(length = 191)
    @GeneratedValue(generator = "CUID")
    @GenericGenerator(name = "CUID", strategy = "com.moigae.application.core.config.PrimaryGenerator")
    private String id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth")
    private LocalDate birth;

    @Column
    @Embedded
    private Address address;

    @Column(name = "account")
    private String account;

    @Column(name = "self_introduction", length = 100)
    private String hostIntroduction;

    @Column(name = "email")
    private String email;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    //회원 탈퇴 시, false 값일 경우 batch 처리
    @Column(name = "flag")
    private boolean flag;

    //회원 탈퇴 시, 회원 탈퇴 시점이 3개월 이상 -> batch 처리
    @Column(name = "deactivateAt")
    private LocalDateTime deactivateAt;

    @Builder
    public User(String userName, String password, Gender gender, String phone, LocalDate birth,
                Address address, String account, String hostIntroduction, String email, UserRole userRole,
                boolean flag, LocalDateTime deactivateAt) {
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