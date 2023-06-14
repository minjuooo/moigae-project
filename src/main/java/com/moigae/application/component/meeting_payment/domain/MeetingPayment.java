package com.moigae.application.component.meeting_payment.domain;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting_payment.domain.enumeration.Payment;
import com.moigae.application.component.meeting_payment.domain.enumeration.RefundStatus;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.core.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "meeting_payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingPayment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    //결제 수단 ENUM - CARD
    @Column(name = "payment")
    @Enumerated(EnumType.STRING)
    private Payment payment;

    //결제된 금액
    @Column(name = "paid_amount")
    private Long paidAmount;

    //정산된 금액
    @Column(name = "amount")
    private Long amount;

    //환불 체크
    @Column(name = "refund_status")
    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus;

    @Builder
    public MeetingPayment(User user, Meeting meeting, Payment payment, Long paidAmount, Long amount,
                          RefundStatus refundStatus) {
        this.user = user;
        this.meeting = meeting;
        this.payment = payment;
        this.paidAmount = paidAmount;
        this.amount = amount;
        this.refundStatus = refundStatus;
    }
}