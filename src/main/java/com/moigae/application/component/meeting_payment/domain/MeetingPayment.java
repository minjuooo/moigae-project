package com.moigae.application.component.meeting_payment.domain;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting_payment.domain.enumeration.RefundStatus;
import com.moigae.application.component.meeting_payment.domain.enumeration.TradeStatus;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.core.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "meeting_payment")
@NoArgsConstructor
@Getter
public class MeetingPayment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    //해당 결제(신청) 내역이 무료인지, 유료인지 판단.
    //결제면 결제, 무료면 신청
    //아니면 결제 금액을 넣어 놓을 수도 있다.
    //ex) 1000(유료), 0(무료)
    //

    //결제 (성공, 실패) 여부
    @Column(name = "trade_status")
    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;

    //주문 번호
    @Column(name = "meeting_order_id")
    private String meetingOrderId;

    //결제된 금액
    @Column(name = "paid_amount")
    private Long paidAmount;

    //정산된 금액
    @Column(name = "calculate_amount")
    private Long calculateAmount;

    //환불 체크
    @Column(name = "refund_status")
    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus;

    @Builder
    public MeetingPayment(User user, Meeting meeting, TradeStatus tradeStatus, String meetingOrderId,
                          Long paidAmount, Long calculateAmount, RefundStatus refundStatus) {
        this.user = user;
        this.meeting = meeting;
        this.tradeStatus = tradeStatus;
        this.meetingOrderId = meetingOrderId;
        this.paidAmount = paidAmount;
        this.calculateAmount = calculateAmount;
        this.refundStatus = refundStatus;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}