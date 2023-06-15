package com.moigae.application.component.meeting_payment.repository;

import com.moigae.application.component.meeting_payment.domain.MeetingPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingPaymentRepository extends JpaRepository<MeetingPayment, Long> {
}