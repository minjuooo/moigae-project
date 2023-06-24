package com.moigae.application.component.meeting_user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CalculationDto {
    private String meetingTitle;
    private LocalDateTime meetingEndDateTime;
    private Integer meetingAmount;
    private int currentParticipants;
    private Long calculateAmount;
}