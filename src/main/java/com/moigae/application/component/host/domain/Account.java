package com.moigae.application.component.host.domain;

import com.moigae.application.component.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "account")
@NoArgsConstructor
@Getter
public class Account {
    //extends BaseEntity
    @Id
    @Column(length = 191)
    @GeneratedValue(generator = "CUID")
    @GenericGenerator(name = "CUID", strategy = "com.moigae.application.core.config.PrimaryGenerator")
    private String id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    //meeting이랑
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Account(String id, String accountNumber, String bankName) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }
}