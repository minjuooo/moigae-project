package com.moigae.application.component.host.dto;

import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccountDto {
    private String id;
    private String meetingUserId;
    private String bankName;
    private String accountNumber;
    private LocalDateTime date;
    private MeetingStatus meetingStatus;
    private String meetingTitle;
    private MeetingPrice price;
    private ParticipantRange participantRange;

    //총 정산합계
//    public BigDecimal getTotalAmount() {
//        if (price != null && participantRange != null) {
//            int quantity = participantRange.getCurrentParticipants();
//            BigDecimal amountPerParticipant = getTotalAmount();
//            return amountPerParticipant.multiply(BigDecimal.valueOf(quantity));
//        } else {
//            return BigDecimal.ZERO;
//        }
//    }
}