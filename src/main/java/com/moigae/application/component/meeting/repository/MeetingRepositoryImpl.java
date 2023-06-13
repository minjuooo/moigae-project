package com.moigae.application.component.meeting.repository;

import com.moigae.application.component.meeting.api.request.MeetingCategoryDto;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.QMeeting;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.querydsl.core.types.OrderSpecifier;
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
    public Page<MeetingDto> findMeetingsByCondition(String sort, MeetingCategoryDto meetingCategoryDto,
                                                    String searchTitle, Pageable pageable) {
        //정렬 체크
        OrderSpecifier<?> sortOrder = isSortConditions(sort);

        //쿼리
        List<Meeting> findMeetings = queryFactory.selectFrom(QMeeting.meeting)
                .where(isCategories(meetingCategoryDto))
                .orderBy(sortOrder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //Dto 변환
        List<MeetingDto> meetingDtoList = findMeetings.stream()
                .map(MeetingDto::toMeetingDto)
                .collect(Collectors.toList());

        //DtoList -> Page 변환
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), meetingDtoList.size());

        return new PageImpl<>(meetingDtoList.subList(start, end), pageable, meetingDtoList.size());
    }

    private OrderSpecifier<?> isSortConditions(String sort) {
        if (sort.equals("latest")) {
            return QMeeting.meeting.createTime.asc();
        } else if (sort.equals("ordest")) {
            return QMeeting.meeting.createTime.desc();
        } else if (sort.equals("popular")) {
            return QMeeting.meeting.createTime.asc(); // 이 부분은 인기도 기준으로 변경 필요
        } else {
            return QMeeting.meeting.createTime.asc();
        }
    }

    private BooleanExpression isCategories(MeetingCategoryDto meetingCategoryDto) {
        BooleanExpression expression = null;
        // meetingCategoryDto.toMeetingCategoryDtoList() 가 빈 목록일 경우
        // QMeeting.meeting.isNotNull() 을 반환하여 모든 Meeting 반환
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