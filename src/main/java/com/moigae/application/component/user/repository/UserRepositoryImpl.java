package com.moigae.application.component.user.repository;

import com.moigae.application.component.user.domain.QUser;
import com.moigae.application.component.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public User findCustomUserById(String userId) {
        return queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.id.eq(userId))
                .fetchFirst();
    }
}