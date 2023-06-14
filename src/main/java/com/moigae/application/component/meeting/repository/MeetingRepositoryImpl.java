package com.moigae.application.component.meeting.repository;

import com.moigae.application.component.meeting.api.request.MeetingCategoryDto;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.QMeeting;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MeetingDto> findMeetingsByCondition(MeetingCategoryDto meetingCategoryDto, Pageable pageable) {
        if (meetingCategoryDto.toMeetingCategoryDtoList().isEmpty()) {
            //쿼리
            QueryResults<Meeting> results = queryFactory
                    .selectFrom(QMeeting.meeting)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();

            //Dto 변환
            List<MeetingDto> meetingDtoList = results.getResults().stream()
                    .map(MeetingDto::toMeetingDto)
                    .collect(Collectors.toList());
            long total = results.getTotal();

            return new PageImpl<>(meetingDtoList, pageable, total);
        }
        //쿼리
        QueryResults<Meeting> results = queryFactory
                .selectFrom(QMeeting.meeting)
                .where(isCategories(meetingCategoryDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        //Dto 변환
        List<MeetingDto> meetingDtoList = results.getResults().stream()
                .map(MeetingDto::toMeetingDto)
                .collect(Collectors.toList());
        long total = results.getTotal();

        return new PageImpl<>(meetingDtoList, pageable, total);
    }

    private BooleanExpression isCategories(MeetingCategoryDto meetingCategoryDto) {
        BooleanExpression expression = null;
        for (String category : meetingCategoryDto.toMeetingCategoryDtoList()) {
            BooleanExpression categoryExpression = QMeeting.meeting.meetingCategory.stringValue().eq(category);
            expression = isExpressionConditions(expression, categoryExpression);
        }
        return Optional.ofNullable(expression)
                .orElse(QMeeting.meeting.isNotNull());
    }

    private static BooleanExpression isExpressionConditions(BooleanExpression expression, BooleanExpression categoryExpression) {
        if (expression == null) {
            expression = categoryExpression;
        } else {
            expression = expression.or(categoryExpression);
        }
        return expression;
    }
}